package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO to reset the password with the corresponding email address.
 */
public record ForgetPasswordRequest(
    @NotNull(message = "{email.required}")
    @Email(message = "{email.invalid}")
    String email
) {
    public ForgetPasswordRequest {
        if(email != null) email = email.toLowerCase();
    }
}
