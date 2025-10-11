package worker

import (
	"context"
	"encoding/json"
	"fmt"
	"log"

	"shahrohit.com/internal/rabbitmq"
	"shahrohit.com/internal/types"
)

type Service struct {
	Cons   *rabbitmq.Consumer
	Pub    *rabbitmq.Publisher
	Names  rabbitmq.Names
	Eval func(req types.Submission) (types.JudgeSummary)
}

/**
 * Worker Server - consuming, evaluating, and publishing results
 */
func (s *Service) Run(ctx context.Context) error {
	if err := s.Cons.Prefetch(10); err != nil {
		return err
	}

	deliveries, err := s.Cons.Deliveries()
	if err != nil { return err }

	log.Println("Hashcodex Judge is listening on Queue:", s.Names.ReqQueue)

	for{
        select {
		case <-ctx.Done():
			log.Println("shutting down...")
			return nil
		case d, ok := <-deliveries:
			if !ok {
				log.Println("Delivery Channel Closed")
				return nil
			}

			// protect against empty bodies
            if len(d.Body) == 0 {
                log.Printf("Empty body, corr=%s", d.CorrelationId)
                _ = d.Nack(false, false)
                continue
            }

			// protect against empty bodies
			var req types.Submission
			if err := json.Unmarshal(d.Body, &req); err != nil {
				log.Printf("Invalid Data Provided, corr=%s", d.CorrelationId)
				_ = d.Nack(false, false)
				continue
			}

			result := s.Eval(req)

			body, err := json.Marshal(result)
            if err != nil {
                log.Printf("Marshal result failed, corr=%s", d.CorrelationId)
                _ = d.Nack(false, false)
                continue
            }

			if err := s.Pub.PublishJSON(ctx, s.Names.ResExchange, s.Names.ResRoutingKey, d.CorrelationId, body); err != nil {
				_ = d.Nack(false, true)
				continue
			}

			if err := d.Ack(false); err != nil {
                log.Printf("Ack failed for submission %d: %v, corr=%s", req.SubmissionId, err, d.CorrelationId)
                return fmt.Errorf("ack failed: %w", err)
            }
		}
    }
}

