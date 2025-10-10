package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO to create problem testcase
 */
public record CreateTestcaseRequest(
    @NotBlank(message = "{field.required}")
    String input,

    @NotBlank(message = "{field.required}")
    String output,

    @NotNull(message = "{field.required}")
    Boolean sample
) {}
