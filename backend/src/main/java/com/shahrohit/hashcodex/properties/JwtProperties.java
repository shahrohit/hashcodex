package com.shahrohit.hashcodex.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for JWT secret and expiration.
 * <p>Binds properties with the prefix {@code app.jwt} from application.properties.</p>
 */
@Validated
@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
    @NotBlank(message = "JWT Secret is required")
    @Size(min = 256, message = "JWT Secret should be at least 256 characters")
    String secret,

    @Positive(message = "Access Token expiration must be positive")
    long accessTokenExpirationMs,

    @Positive(message = "Refresh Token expiration must be positive")
    long refreshTokenExpirationMs
) {}
