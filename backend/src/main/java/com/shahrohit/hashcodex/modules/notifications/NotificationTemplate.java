package com.shahrohit.hashcodex.modules.notifications;

/**
 * Utility class that provides predefined {@link NotificationPayload} templates.
 * <p>This class is not intended to be instantiated.</p>
 */
public final class NotificationTemplate {
    private NotificationTemplate() {
    }

    public static NotificationPayload resetPassword(String name, String email, String verificationLink) {
        String body = """
              <div style="font-family: Arial, sans-serif; color: #333;">
                <p><b>Hi %s,</b></p>
                <br/>
                <p>We received a request to reset your password for your <b>Hashcodex</b> account.\s
                Click the button below to set a new password:</p>
                <p>
                    <a href="%s"\s
                       style="background-color: #FF5722; color: white; padding: 10px 20px;\s
                              text-decoration: none; border-radius: 5px;">
                       Reset Password
                    </a>
                </p>
                <br/>
                <p>If the button doesn’t work, copy and paste this link into your browser:</p>
                <p><a href="%s">%s</a></p>
                <br/>
                <p><b>Note:</b> This link will expire in 15 minutes for your security.\s
                If you did not request a password reset, you can safely ignore this email.</p>
            </div>
            """.formatted(name, verificationLink, verificationLink, verificationLink);

        String subject = "Reset Your Password - Hashcodex";
        return new EmailPayload(email, subject, body);
    }

    public static NotificationPayload verifyEmail(String name, String email, String verificationLink) {
        String body = """
            <div style="font-family: Arial, sans-serif; color: #333;">
                <p><b>Hi %s,</b></p>
                <p>Thank you for registering with <b>Hashcodex</b>. To complete your registration, please verify your account by clicking the button below:</p>
                <br/>
                <p>
                    <a href="%s"
                       style="background-color: #4CAF50; color: white; padding: 10px 20px;
                              text-decoration: none; border-radius: 5px;">
                       Verify Account
                    </a>
                </p>
                <br/>
                <p>If the button doesn’t work, copy and paste this link into your browser:</p>
                <p><a href="%s">%s</a></p>
            </div>
            """.formatted(name, verificationLink, verificationLink, verificationLink);

        String subject = "Verify your Account - Hashcodex";
        return new EmailPayload(email, subject, body);
    }
}
