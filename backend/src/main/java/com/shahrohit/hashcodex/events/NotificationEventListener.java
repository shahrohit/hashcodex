package com.shahrohit.hashcodex.events;

import com.shahrohit.hashcodex.properties.ProfileProperties;
import com.shahrohit.hashcodex.modules.notifications.NotificationPayload;
import com.shahrohit.hashcodex.modules.notifications.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component that listens to application events carrying {@link NotificationPayload}
 * and dispatches them to appropriate {@link Notifier} implementations.
 *
 * <p>Supports both single and batch notifications.</p>
 * <p>Uses asynchronous execution for single payload handling to avoid blocking the main thread.</p
 */
@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final List<Notifier<? extends NotificationPayload>> notifiers;
    private final ProfileProperties profileProperties;

    /**
     * Event Listner when sending notification
     *
     * @param payload - Notification Payload e.g. Email notification
     */
    @Async
    @EventListener
    public void handleNotification(NotificationPayload payload) {
        switch (profileProperties.active()) {
            case DEV -> {
                System.out.println("To: " + payload.recipient());
                System.out.println(payload.content());
            }

            case PROD -> notifiers.stream()
                .filter(notifier -> notifier.getPayloadType().isInstance(payload))
                .findFirst()
                .ifPresent(notifier -> NotificationEventListener.sendNotification(notifier, payload));
        }
    }

    /**
     * Send Notification via Notifier
     */
    private static <T extends NotificationPayload> void sendNotification(
        Notifier<T> notifier,
        NotificationPayload payload
    ) {
        T typedPayload = notifier.getPayloadType().cast(payload);
        notifier.send(typedPayload);
    }
}
