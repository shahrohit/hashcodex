package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO to submit code
 */
public record SubmitCodeRequest(
    @NotNull(message = "Language is Required")
    Language language,

    @NotNull(message = "Code is Required")
    String code
) {}
