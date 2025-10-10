package com.shahrohit.hashcodex.queues;

import com.shahrohit.hashcodex.enums.ProblemSubmissionStatus;
import com.shahrohit.hashcodex.enums.SubmissionType;
import com.shahrohit.hashcodex.events.ServerSentEventHub;
import com.shahrohit.hashcodex.repositories.ProblemSubmissionRepository;
import com.shahrohit.hashcodex.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Consumer service for handling submission results from RabbitMQ.
 * <p>
 * This service listens to the configured result queue and processes
 * {@link SubmissionResult} messages. The raw AMQP message is also
 * available for accessing metadata such as correlation ID, headers, etc.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class SubmissionConsumer {
    private final ProblemSubmissionRepository problemSubmissionRepository;
    private final ServerSentEventHub serverSentEventHub;

    /**
     * Processes incoming {@link SubmissionResult} messages from the result queue.
     * @param result the deserialized submission result
     * @param raw    the raw AMQP message, containing metadata like headers and correlation ID
     */
    @RabbitListener(queues = Constants.RABBITMQ.RES_QUEUE)
    public void onResult(SubmissionResult result, Message raw) {
        String corr = raw.getMessageProperties().getCorrelationId();

        if (result == null) {
            serverSentEventHub.send(corr, "verdict", null);
            return;
        }

        if (result.id() != null && Objects.equals(result.submissionType(), SubmissionType.SUBMIT)) {
            try {
                ProblemSubmissionStatus status = ProblemSubmissionStatus.valueOf(result.status());
                problemSubmissionRepository.updateSubmissionStatus(result.id(), status);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid submission status");
            }
        }


        String errorMessage = getString(result);
        SubmissionResult response = new SubmissionResult(
            null,
            result.total(),
            result.passed(),
            result.status(),
            result.compileError(),
            result.timeMs(),
            result.cases(),
            errorMessage,
            result.submissionType()
        );

        serverSentEventHub.send(corr, "verdict", response);
    }

    private static String getString(SubmissionResult result) {
        String errorMessage = null;
        if (result.errorMessage() != null && !result.errorMessage().isEmpty()) {
            errorMessage = switch (result.errorMessage()) {
                case "run:type_mismatch" -> "Cannot Run your code";
                case "run:ws_failed" -> "Failed to Run with custom testcases.";
                case "run:compile_failed" -> "Failed to compile with custom testcases.";
                case "run:failed" -> "Failed to run with custom testcases. Make sure your input testcases are valid";
                case "sub:type_mismatch" -> "Cannot Submit your code";
                case "sub:ws_failed" -> "Failed to Submit your code";
                case "lang" -> "Language Not Supported";
                default -> null;
            };
        }
        return errorMessage;
    }
}
