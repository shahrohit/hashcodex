package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO to update problem description
 */
public record UpdateProblemDescriptionRequest(
    @NotBlank(message = "{field.required}")
    String description
) {
}
