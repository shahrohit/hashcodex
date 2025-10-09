package com.shahrohit.hashcodex.modules.notifications;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * {@link Notifier} implementation responsible for handling {@link EmailPayload} notifications.
 * <p>This component uses {@link JavaMailSender} to send emails.</p>
 */
@Component
@RequiredArgsConstructor
public class EmailNotifier implements Notifier<EmailPayload> {
    private final JavaMailSender mailSender;

    @Override
    public void send(EmailPayload payload) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(payload.recipient());
            helper.setSubject(payload.subject());
            helper.setText(payload.content(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the class type of the payload this notifier supports.
     * Used by the event listener to match payloads to notifiers.
     *
     * @return {@link EmailPayload}.class
     */
    @Override
    public Class<EmailPayload> getPayloadType() {
        return EmailPayload.class;
    }
}
