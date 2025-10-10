package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO to update code
 */
public record UpdateCodeRequest(
    @NotNull(message = "{language.required}")
    Language language,

    @NotBlank(message = "{code.required}")
    String code
) {}
