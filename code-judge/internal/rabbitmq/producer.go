package rabbitmq

import (
	"context"
	"time"

	amqp "github.com/rabbitmq/amqp091-go"
)

/**
 * Creating new consumer
 */
func NewPublisher(ch *amqp.Channel, n Names) *Publisher {
	return &Publisher{ch: ch, n: n}
}

/**
 * Push the result into the Queue
 */
func (p *Publisher) PublishJSON(ctx context.Context, exchange, routingKey string, correlationId string, body []byte,) error {
	return p.ch.PublishWithContext(ctx, exchange, routingKey, false, false, amqp.Publishing{
		ContentType:   "application/json",
		Body:          body,
		DeliveryMode:  amqp.Persistent,
		CorrelationId: correlationId,
		Timestamp:     time.Now(),
	})
}
