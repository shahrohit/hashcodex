package judge

import (
	"context"
	"errors"
	"strings"
	"time"

	"shahrohit.com/internal/executor"
	"shahrohit.com/internal/langs"
	"shahrohit.com/internal/types"
	"shahrohit.com/internal/utils"
)

type Options struct {
	Compile types.Limits
	Run     types.Limits
}

func RunWithCustomTestcases(ctx context.Context, ex *executor.Executor, body types.Submission) types.JudgeSummary {
	if(body.Type != "RUN" || body.SolutionCode == "" || body.Code == "") {
		return  types.JudgeSummary{Status: types.VerdictSERVER, ErrorMessage: "Failed to run your code"}
	}

	var langSpecs, err = getLanguageSpecs(body.Language, body.SolutionCode)
	if(err != nil) { return types.JudgeSummary{Status: types.VerdictSERVER, ErrorMessage: err.Error()} }

	sum := types.JudgeSummary{}
	ws, err := ex.NewWorkspace(ctx)
	if err != nil { 
		sum.Status = types.VerdictSERVER
		sum.ErrorMessage = "Failed to run your code"
		return sum
	}

	defer ex.RemoveWorkspace(ws)

	opts := Options{
		Compile: types.Limits{MemBytes: 512 * 1024 * 1024, CPUNanos: 2_000_000_000, PidsLimit: 256, Timeout: 5 * time.Second},
		Run:     types.Limits{MemBytes: 256 * 1024 * 1024, CPUNanos: 1_000_000_000, PidsLimit: 128, Timeout: time.Duration(utils.GetTimeLimit(body.Language, body.TimeLimit)) * time.Second},
	}

	// Compile Solution Code
	if strings.TrimSpace(langSpecs.CompileScript) != "" {
		compRes, err := ex.Run(ctx, ws, langSpecs.Image, []string{"sh", "-lc", langSpecs.CompileScript}, false, "", opts.Compile)
		
		if(err != nil || compRes.TimedOut || compRes.ExitCode != 0){
			sum.Status = types.VerdictSERVER
			sum.ErrorMessage = "Cannot run with custom testcases";
			return sum;
		}
	}

	testcases := []types.Testcase{}

	for _, tc := range body.Testcases {
		res, _ := ex.Run(ctx, ws, langSpecs.Image, langSpecs.RunCmd, true, tc.Input, opts.Run)

		if(res.TimedOut || res.OOMKilled || res.ExitCode != 0){
			sum.Status = types.VerdictRE
			sum.ErrorMessage = utils.ReformatError(res.Stderr, body.StartLine, body.Language);
			return sum;
		}

		output := strings.TrimSpace(res.Stdout)
		testcases = append(testcases, types.Testcase{Input: tc.Input, Expected: output})
	}

	langSpecs, err = getLanguageSpecs(body.Language, body.Code);
	if(err != nil) { return types.JudgeSummary{Status: types.VerdictSERVER, ErrorMessage: err.Error()} }

	sum.Total = len(testcases)

	// Compile User Code
	if strings.TrimSpace(langSpecs.CompileScript) != "" {
		compRes, err := ex.Run(ctx, ws, langSpecs.Image, []string{"sh", "-lc", langSpecs.CompileScript}, false, "", opts.Compile)
		if(compRes.Stderr != ""){
        	sum.CompileError = utils.ReformatError(compRes.Stderr, body.StartLine,body.Language)
		}
		switch {
		case err != nil:
			sum.Status = types.VerdictCE
			return sum
		case compRes.TimedOut:
			sum.Status = types.VerdictTLE
			return sum
		case compRes.ExitCode != 0:
			sum.Status = types.VerdictCE
			return sum
		}
	}

	sum.Cases = []types.CaseResult{}
	for _, tc := range testcases {
		res, _ := ex.Run(ctx, ws, langSpecs.Image, langSpecs.RunCmd, true, tc.Input, opts.Run)

		verdict := types.VerdictAC
		output := strings.TrimSpace(res.Stdout)
		expected := strings.TrimSpace(tc.Expected)

		switch {
			case res.TimedOut:
				verdict = types.VerdictTLE
			case res.OOMKilled:
				verdict = types.VerdictMLE
			case res.ExitCode != 0:
				verdict = types.VerdictRE
			case output != expected:
				verdict = types.VerdictWA
		}

		if(res.Stderr != ""){ res.Stderr = utils.ReformatError(res.Stderr, body.StartLine, body.Language) }
		
		sum.Cases = append(sum.Cases, types.CaseResult{
			Input: tc.Input,
			Output: output,
			Expected: expected,
			Error: res.Stderr,
			Status: verdict,
		})

		if verdict != types.VerdictAC {
			sum.Status = verdict
		} else {
			sum.Passed++
		}
		sum.TimeMs += res.DurationMs
	}
	if(sum.Passed == sum.Total) { sum.Status = types.VerdictAC }
	return sum

}

func RunWithPredefinedTestcase(ctx context.Context, ex *executor.Executor, body types.Submission) types.JudgeSummary {
	if(body.Type != "SUBMIT" || body.Code == "") {
		return  types.JudgeSummary{Status: types.VerdictSERVER, ErrorMessage: "Failed to submit your code"}
	}

	var langSpecs, err = getLanguageSpecs(body.Language, body.Code)
	if(err != nil) { return types.JudgeSummary{Status: types.VerdictSERVER, ErrorMessage: err.Error()} }

	sum := types.JudgeSummary{Total: len(body.Testcases)}

	// Workspace
	ws, err := ex.NewWorkspace(ctx)
	if err != nil { 
		sum.Status = types.VerdictSERVER
		sum.ErrorMessage = "Failed to Submit your code"
		return  sum
	}

	defer ex.RemoveWorkspace(ws)

	opts := Options{
		Compile: types.Limits{MemBytes: 512 * 1024 * 1024, CPUNanos: 2_000_000_000, PidsLimit: 256, Timeout: 5 * time.Second},
		Run:     types.Limits{MemBytes: 256 * 1024 * 1024, CPUNanos: 1_000_000_000, PidsLimit: 128, Timeout: time.Duration(utils.GetTimeLimit(body.Language, body.TimeLimit)) * time.Second},
	}

	// Compile
	if strings.TrimSpace(langSpecs.CompileScript) != "" {
		compRes, err := ex.Run(ctx, ws, langSpecs.Image, []string{"sh", "-lc", langSpecs.CompileScript}, false, "", opts.Compile)
		if(compRes.Stderr != ""){
        	sum.CompileError = utils.ReformatError(compRes.Stderr, body.StartLine, body.Language)
		}
		switch {
		case err != nil:
			sum.Status = types.VerdictCE
			return sum
		case compRes.TimedOut:
			sum.Status = types.VerdictTLE
			return sum
		case compRes.ExitCode != 0:
			sum.Status = types.VerdictCE
			return sum
		}
	}

	sum.Cases = []types.CaseResult{}
	for _, tc := range body.Testcases {
		res, _ := ex.Run(ctx, ws, langSpecs.Image, langSpecs.RunCmd, true, tc.Input, opts.Run)

		verdict := types.VerdictAC
		output := strings.TrimSpace(res.Stdout)
		expected := strings.TrimSpace(tc.Expected)

		switch {
			case res.TimedOut:
				verdict = types.VerdictTLE
			case res.OOMKilled:
				verdict = types.VerdictMLE
			case res.ExitCode != 0:
				verdict = types.VerdictRE
			case output != expected:
				verdict = types.VerdictWA
		}

		if verdict != types.VerdictAC {
			sum.Status = verdict
			if(res.Stderr != ""){
				res.Stderr = utils.ReformatError(res.Stderr, body.StartLine, body.Language)
			}
			sum.Cases = append(sum.Cases, types.CaseResult{
				Input:    tc.Input,
				Output:   output,
				Expected: expected,
				Error:    res.Stderr,
				Status:   verdict,
			})
			return sum
		}

		sum.Passed++
		sum.TimeMs += res.DurationMs
	}

	sum.Status = types.VerdictAC	
	return sum

}

func getLanguageSpecs(language string, code string) (types.LangSpec,error) {
	switch(language){
	case "java":
		return langs.JavaSpec(code), nil
	case "cpp":
		return langs.CppSpec(code), nil
	case "python":
		return langs.PythonSpec(code), nil
	}
	return types.LangSpec{}, errors.New("language Not Supported")
}
