package com.shahrohit.hashcodex.modules.notifications;

/**
 * A common contract for different types of notification payloads
 * (e.g., Email, Push).
 * <p>
 * This sealed interface ensures only permitted implementations can be used,
 * enforcing type safety and consistency across all notification types.
 * </p>
 *
 * @see EmailPayload
 */
public sealed interface NotificationPayload permits EmailPayload {

    /**
     * Returns the recipient of the notification.
     */
    String recipient();

    /**
     * Returns the main content or body of the notification.
     */
    String content();
}
