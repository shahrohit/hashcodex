package com.shahrohit.hashcodex.properties;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for frontend urls.
 * <p>Binds properties with the prefix {@code app.frontend} from application.properties.</p>
 */
@Validated
@ConfigurationProperties(prefix = "app.frontend")
public record FrontendProperties(
    @NotBlank(message = "Frontend URL of user is required")
    @URL(message = "Invalid URL")
    String url
) {}
