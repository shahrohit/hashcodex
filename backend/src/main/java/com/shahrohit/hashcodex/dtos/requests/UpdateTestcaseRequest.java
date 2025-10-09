package com.shahrohit.hashcodex.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateTestcaseRequest(
    @NotBlank(message = "{field.required}")
    String content
) {}
