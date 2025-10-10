package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO to update testcases
 */
public record UpdateTestcaseRequest(
    @NotBlank(message = "{field.required}")
    String content
) {}
