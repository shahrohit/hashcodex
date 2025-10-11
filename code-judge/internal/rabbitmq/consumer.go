package rabbitmq

import (
	amqp "github.com/rabbitmq/amqp091-go"
)

/**
 * Creating new consumer
 */
func NewConsumer(ch *amqp.Channel, queue string) *Consumer {
	return &Consumer{ch: ch, queue: queue}
}

// Prefetch sets QoS for fair dispatch.
func (c *Consumer) Prefetch(count int) error {
	return c.ch.Qos(count, 0, false)
}

// Deliveries returns a channel of AMQP deliveries.
func (c *Consumer) Deliveries() (<-chan amqp.Delivery, error) {
	return c.ch.Consume(c.queue, "judge-worker", false, false, false, false, nil)
}