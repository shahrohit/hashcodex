package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO to log in with email and password.
 */
public record EmailLoginRequest(
    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    String email,

    @NotBlank(message = "{password.required}")
    String password
) {
    public EmailLoginRequest{
        if(email != null) email = email.toLowerCase();
    }
}
