package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request DTO to verify user's email/account with token.
 */
public record VerifyEmailRequest(
    @NotBlank(message = "{token.required}")
    String token,

    @NotNull(message = "Missing id")
    UUID publicId
) {}
