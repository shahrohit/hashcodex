package com.shahrohit.hashcodex.modules.notifications;

/**
 * Payload for sending email notification.
 */
public record EmailPayload(String recipient, String subject, String content) implements NotificationPayload {}
