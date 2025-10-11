package rabbitmq

import (
	amqp "github.com/rabbitmq/amqp091-go"
)

/**
 * Connect to Rabbit MQ server using the Connection URL
 */
func Dial(url string) (*Conn, error) {
    c, err := amqp.Dial(url)
    if err != nil { return nil, err }
    return &Conn{raw: c}, nil
}

/**
 * Close the connection
 */
func (c *Conn) Close() error {
    return c.raw.Close()
}

/**
 * Create Channel
 */
func (c *Conn) Channel() (*Channel, error) {
    ch, err := c.raw.Channel()
    if err != nil { return nil, err }
    return &Channel{raw: ch}, nil
}

/**
 * Create the Channel
 */
func (ch *Channel) Close() error {
    return ch.raw.Close()
}

/**
 * Declare Topology (Exhange, Queue, Routing Key)
 */
func DeclareTopology(ch *amqp.Channel, names Names) error {
	err := ch.ExchangeDeclare(names.ReqExchange, "direct", true, false, false, false, nil); 
	if err != nil { return err }

	err = ch.ExchangeDeclare(names.ResExchange, "direct", true, false, false, false, nil); 
	if err != nil { return err }

	_, err = ch.QueueDeclare(names.ReqQueue, true, false, false, false, nil);
	if err != nil { return err }
	
	_, err = ch.QueueDeclare(names.ResQueue, true, false, false, false, nil);
	if err != nil { return err }
	

	err = ch.QueueBind(names.ReqQueue, names.ReqRoutingKey, names.ReqExchange, false, nil); 
	if err != nil { return err }

	err = ch.QueueBind(names.ResQueue, names.ResRoutingKey, names.ResExchange, false, nil); 
	if err != nil { return err }
    return nil
}

// Helper to get underlying amqp objects if needed
func (c *Conn) Raw() *amqp.Connection   { return c.raw }
func (ch *Channel) Raw() *amqp.Channel { return ch.raw }
