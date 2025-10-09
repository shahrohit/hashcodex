package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Request DTO to confirm the password update.
 */
public record ResetPasswordRequest(
    @NotBlank(message = "{token.required}")
    String token,

    @NotBlank(message = "{password.required}")
    @Size(min = 6, message = "{password.size}")
    String password,

    @NotBlank(message = "{password.confirm.required}")
    @Size(min = 6, message = "{password.size}")
    String confirmPassword,

    @NotNull(message = "Missing Id")
    UUID publicId
) {}
