package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO to create Code
 */
public record CreateCodeRequest(
    @NotNull(message = "{field.required}")
    Language language,

    @NotBlank(message = "{field.required}")
    String driverCode,

    @NotBlank(message = "{field.required}")
    String userCode,

    @NotBlank(message = "{field.required}")
    String solutionCode
) {}
