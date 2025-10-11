package rabbitmq

import "github.com/rabbitmq/amqp091-go"

type Conn struct {
    raw *amqp091.Connection
}

type Channel struct {
    raw *amqp091.Channel
}

type Consumer struct {
	ch    *amqp091.Channel
	queue string
}

type Publisher struct {
	ch *amqp091.Channel
	n  Names
}