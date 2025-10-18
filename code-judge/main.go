package main

import (
	"context"
	"log"
	"os"
	"os/signal"

	"shahrohit.com/internal/judge"
	"shahrohit.com/internal/rabbitmq"
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
		Eval:  judge.CodeSubmission,
	}

	// Run with graceful shutdown
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
	defer stop()

	if err := svc.Run(ctx); err != nil && ctx.Err() == nil { log.Fatalf("run: %v", err) }
}


