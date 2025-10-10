package com.shahrohit.hashcodex.queues;

import com.shahrohit.hashcodex.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for sending submission requests to RabbitMQ.
 * <p>
 * This producer sends {@link SubmissionRequest} objects to the configured
 * request exchange using the defined routing key. Each message is assigned
 * a unique correlation ID for tracking purposes.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class SubmissionProducer {
    private final RabbitTemplate rabbitTemplate;

    /**
     * Sends a {@link SubmissionRequest} message to the configured RabbitMQ exchange
     *
     * @param request the submission request object to send
     * @return the unique correlation ID assigned to this message
     */
    public String send(SubmissionRequest request) {
        String correlationId = UUID.randomUUID().toString();

        rabbitTemplate.convertAndSend(
            Constants.RABBITMQ.REQ_EXCHANGE,
            Constants.RABBITMQ.REQ_ROUTING_KEY,
            request,
            message -> {
                message.getMessageProperties().setCorrelationId(correlationId);
                return message;
            }
        );
        return correlationId;
    }

}
