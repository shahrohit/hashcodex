package rabbitmq

import "os"

type Names struct {
	ReqExchange string
	ResExchange string
	ReqRoutingKey string
	ResRoutingKey string
	ReqQueue string
	ResQueue string
}

type Config struct {
	URL string
	Names Names
}

/**
 * Return the Rabbit MQ connection URL either from the environemnt variable or default URL
*/
func ConnectionURL() string {
	if v := os.Getenv("AMQP_URL"); v != "" { return v}
	return "amqp://guest:guest@localhost:5672/";
}

/**
 * Rabbit MQ names for Exchange, Queue, and Routing Key for both Request and Response
*/
func DefaultNames() Names {
	return Names{
		ReqExchange:   "hashcodex.req.exchange",
		ResExchange:   "hashcodex.res.exchange",
		ReqRoutingKey: "hashcodex.req",
		ResRoutingKey: "hashcodex.res",
		ReqQueue:      "hashcodex.req.queue",
		ResQueue:	   "hashcodex.res.queue",
	}
}