package executor

import (
	"bytes"
	"context"
	"strings"
	"time"

	"shahrohit.com/internal/types"

	"github.com/docker/docker/api/types/container"
	"github.com/docker/docker/api/types/mount"
	"github.com/docker/docker/api/types/volume"
	"github.com/docker/docker/client"
	"github.com/docker/docker/pkg/stdcopy"
)

type Executor struct {
	cli *client.Client
}

func New() (*Executor, error) {
	cli, err := client.NewClientWithOpts(client.FromEnv, client.WithAPIVersionNegotiation())
	if err != nil { return nil, err }
	return &Executor{cli: cli}, nil
}

func (e *Executor) Close() error { return e.cli.Close() }

// Create workspace volume
func (e *Executor) NewWorkspace(ctx context.Context) (string, error) {
	v, err := e.cli.VolumeCreate(ctx, volume.CreateOptions{})
	if err != nil { return "", err }
	return v.Name, nil
}

// Remove workspace
func (e *Executor) RemoveWorkspace(name string) {
	_ = e.cli.VolumeRemove(context.Background(), name, true)
}

// Run one container with limits, stdin, and buffered logs collection.
func (e *Executor) Run(
	ctx context.Context,
	workspace string,
	image string,
	cmd []string,
	workspaceReadOnly bool,
	stdinData string,
	lim types.Limits,
) (*types.StepResult, error) {
	hostCfg := &container.HostConfig{
		NetworkMode: "none",
		Resources: container.Resources{
			Memory:     lim.MemBytes,
			MemorySwap: lim.MemBytes,
			NanoCPUs:   lim.CPUNanos,
			PidsLimit:  &lim.PidsLimit,
		},
		CapDrop:     []string{"ALL"},
		SecurityOpt: []string{"no-new-privileges"},
		Mounts: []mount.Mount{
			{Type: mount.TypeVolume, Source: workspace, Target: "/workspace", ReadOnly: workspaceReadOnly},
		},
	}
	cfg := &container.Config{
		Image:        image,
		Cmd:          cmd,
		Tty:          false,
		OpenStdin:    true,
		AttachStdin:  true, 
		AttachStdout: false,
		AttachStderr: false,

	}

	ctr, err := e.cli.ContainerCreate(context.Background(), cfg, hostCfg, nil, nil, "")
	if err != nil { return nil, err }
	defer func() { _ = e.cli.ContainerRemove(context.Background(), ctr.ID, container.RemoveOptions{Force: true}) }()

	start := time.Now()

	attach, err := e.cli.ContainerAttach(context.Background(), ctr.ID, container.AttachOptions{
		Stream: true, Stdin: true, Stdout: false, Stderr: false,
	})
	if err != nil { return nil, err }
	defer attach.Close()

	if err := e.cli.ContainerStart(context.Background(), ctr.ID, container.StartOptions{}); err != nil {
		return nil, err
	}
	
	if _, err := attach.Conn.Write([]byte(stdinData + "\n")); err == nil {
		attach.CloseWrite()
	}

	res := &types.StepResult{}
	// Timeout guard
	timeoutCtx, cancel := context.WithTimeout(context.Background(), lim.Timeout)
	defer cancel()
	done := make(chan struct{})
	go func() {
		select {
		case <-timeoutCtx.Done():
			res.TimedOut = true
			_ = e.cli.ContainerKill(context.Background(), ctr.ID, "KILL")
		case <-done:
		}
	}()

	// Wait (use Background to still receive status after kill)
	statusCh, errCh := e.cli.ContainerWait(context.Background(), ctr.ID, container.WaitConditionNotRunning)
	select {
	case <-statusCh:
	case <-errCh:
	}
	close(done)

	// Inspect exit / OOM
	if ins, ierr := e.cli.ContainerInspect(context.Background(), ctr.ID); ierr == nil && ins.State != nil {
		res.ExitCode = ins.State.ExitCode
		if ins.State.OOMKilled { res.OOMKilled = true }
	}

	// Buffered logs
	if lr, lerr := e.cli.ContainerLogs(context.Background(), ctr.ID, container.LogsOptions{
		ShowStdout: true, ShowStderr: true,
	}); lerr == nil {
		var outBuf, errBuf bytes.Buffer
		_, _ = stdcopy.StdCopy(&outBuf, &errBuf, lr)
		_ = lr.Close()
		res.Stdout = strings.TrimSpace(outBuf.String())
		res.Stderr = strings.TrimSpace(errBuf.String())
	}

	res.DurationMs = time.Since(start).Milliseconds()
	return res, nil
}
