package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateProblemDescriptionRequest(
    @NotBlank(message = "{field.required}")
    String description
) {
}
