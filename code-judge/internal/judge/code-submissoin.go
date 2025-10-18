package judge

import (
	"context"
	"log"

	"shahrohit.com/internal/executor"
	"shahrohit.com/internal/types"
)

func CodeSubmission(req types.Submission) types.JudgeSummary {
	ctx := context.Background()

    ex, err := executor.New()
	if err != nil { log.Fatal(err) }
	defer ex.Close()

	// Execute According to the req Type
	// Run with custom testcases, submit code with predefined testcases
	switch(req.Type){
		case "RUN":
			sum := RunWithCustomTestcases(ctx, ex, req)
			sum.Type = req.Type
			return  sum
		case "SUBMIT":
			sum := RunWithPredefinedTestcase(ctx, ex, req)
			sum.Type = req.Type
			sum.Id =  req.SubmissionId
			return  sum
		default:
			return types.JudgeSummary{Type : req.Type, ErrorMessage: "type: not found"}
	}
}