package com.shahrohit.hashcodex.modules.notifications;

/**
 * <p>Defines the contract for sending a notification of a specific payload type.</p>
 * <p>
 * This interface allows flexible, type-safe implementations for various
 * notification channels like Email
 * </p>
 *
 * @param <T> the type of {@link NotificationPayload} this notification can handle
 */
public interface Notifier<T extends NotificationPayload> {

    /**
     * Sends the notification using the given payload.
     *
     * @param payload the payload containing recipient and content information
     */
    void send(T payload);

    /**
     * Returns the class type of the payload this implementation supports.
     * Used for safely matching payloads at runtime.
     *
     * @return the class object for the payload type
     */
    Class<T> getPayloadType();
}
