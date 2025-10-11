package types

import "time"

type Submission struct {
    SubmissionId int64 `json:"submissionId"`
    Language     string `json:"language"`
	SolutionCode string `json:"solutionCode"`
    Code         string `json:"code"`
    Testcases    []Testcase `json:"testcases"`
    StartLine    int    `json:"startLine"`
    Type         string `json:"submissionType"`
}

type Verdict string

const (
	VerdictAC  Verdict = "SOLVED"
	VerdictWA  Verdict = "WA"
	VerdictRE  Verdict = "RTE"
	VerdictTLE Verdict = "TLE"
	VerdictMLE Verdict = "MLE"
	VerdictCE  Verdict = "CTE"
	VerdictSERVER  Verdict = "SERVER_ERROR"
	
)
/* Testcases & Results */
type Testcase struct {
	Input    string `json:"input"`
	Expected string `json:"expected"`
}

type CaseResult struct {
	Input      string  `json:"input,omitempty"`
	Output     string  `json:"output,omitempty"`
	Expected   string  `json:"expected,omitempty"`
	Error     string   `json:"error,omitempty"`
	Status	  		Verdict		 `json:"status"`

}

type JudgeSummary struct {
	Id				int64	  `json:"id,omitempty"`
	Total     		int          `json:"total"`
	Passed    		int          `json:"passed"`
	Status	  		Verdict		 `json:"status"`
	CompileError	string  	 `json:"compileError,omitempty"`
	TimeMs     		int64   	 `json:"timeMs"`
	Cases     		[]CaseResult `json:"cases,omitempty"`
	ErrorMessage 	string		 `json:"errorMessage,omitempty"`
	Type         	string 		 `json:"submissionType"`
}


type LangSpec struct {
	Image         string   // docker image
	CompileScript string   // sh -lc script; writes artifacts to /workspace
	RunCmd        []string // command to run program
}

type Limits struct {
	MemBytes  int64
	CPUNanos  int64
	PidsLimit int64
	Timeout   time.Duration
}

type StepResult struct {
	ExitCode   int
	Stdout     string
	Stderr     string
	TimedOut   bool
	OOMKilled  bool
	DurationMs int64
}
