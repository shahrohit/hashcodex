package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request DTO to run the code
 */
public record RunCodeRequest(
    @NotNull(message = "{language.required}")
    Language language,

    @NotNull(message = "{code.required}")
    String code,

    @NotNull(message = "{testcase.required}")
    List<String> testcases
) {}
