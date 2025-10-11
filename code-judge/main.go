package main

import (
	"context"
	"log"
	"os"
	"os/signal"

	"shahrohit.com/internal/executor"
	"shahrohit.com/internal/judge"
	"shahrohit.com/internal/rabbitmq"
	"shahrohit.com/internal/types"
	"shahrohit.com/internal/worker"
)

func main() {
	// Rabbit MQ configuration
	url := rabbitmq.ConnectionURL();
	names := rabbitmq.DefaultNames();

	// Connect to Rabbit MQ server
	conn, err := rabbitmq.Dial(url)
	if err != nil { log.Fatalf("Failed to connect to rabbitMQ") }
	defer conn.Close()

	// Creating Channel
	channel, err := conn.Channel()
	if err != nil { log.Fatalf("Failed to open a channel") }
	defer channel.Close()

	// Declare topology (exchanges, queues, bindings)
	if err := rabbitmq.DeclareTopology(channel.Raw(), names); err != nil { 
		log.Fatalf("Failed to declare toplogy") 
	}

	// Creating conumer and publisher
	consumer := rabbitmq.NewConsumer(channel.Raw(), names.ReqQueue)
	publisher := rabbitmq.NewPublisher(channel.Raw(), names)

	svc := &worker.Service{
		Cons:  consumer,
		Pub:   publisher,
		Names: names,
		Eval:  submissionCode,
	}

	// Run with graceful shutdown
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
	defer stop()

	if err := svc.Run(ctx); err != nil && ctx.Err() == nil { log.Fatalf("run: %v", err) }
}

func submissionCode(req types.Submission) types.JudgeSummary {
	ctx := context.Background()

    ex, err := executor.New()
	if err != nil { log.Fatal(err) }
	defer ex.Close()

	// Execute According to the req Type
	// Run with custom testcases, submit code with predefined testcases
	switch(req.Type){
		case "RUN":
			sum := judge.RunWithCustomTestcases(ctx, ex, req)
			sum.Type = req.Type
			return  sum
		case "SUBMIT":
			sum := judge.RunWithPredefinedTestcase(ctx, ex, req)
			sum.Type = req.Type
			sum.Id =  req.SubmissionId
			return  sum
		default:
			return types.JudgeSummary{Type : req.Type, ErrorMessage: "type: not found"}
	}
}
