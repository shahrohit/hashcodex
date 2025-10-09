package com.shahrohit.hashcodex.queues;

import com.shahrohit.hashcodex.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SubmissionProducer {
    private final RabbitTemplate rabbitTemplate;

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
