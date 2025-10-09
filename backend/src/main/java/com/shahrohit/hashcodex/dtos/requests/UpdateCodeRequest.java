package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCodeRequest(
    @NotNull(message = "{field.required}")
    Language language,

    @NotBlank(message = "{field.required}")
    String code
) {}
