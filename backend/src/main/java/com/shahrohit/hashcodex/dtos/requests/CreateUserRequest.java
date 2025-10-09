package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO to create user
 */
public record CreateUserRequest(
    @NotBlank(message = "{name.required}")
    @Size(min = 2, max = 50, message = "{name.size}")
    String name,

    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    String email,

    @NotBlank(message = "{password.required}")
    @Size(min = 6, message = "{password.size}")
    String password
) {
    public CreateUserRequest {
        if(name != null) name = name.trim();
        if(email != null) email = email.toLowerCase();
    }
}
