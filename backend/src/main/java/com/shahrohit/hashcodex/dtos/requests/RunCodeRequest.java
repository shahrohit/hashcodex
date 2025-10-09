package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RunCodeRequest(
    @NotNull(message = "Language is Required")
    Language language,

    @NotNull(message = "Code is Required")
    String code,

    @NotNull(message = "Testcases is Required")
    List<String> testcases
) {}
