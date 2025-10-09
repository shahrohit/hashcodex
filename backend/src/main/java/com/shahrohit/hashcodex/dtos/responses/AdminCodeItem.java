package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.Language;

public record AdminCodeItem(
    Long id,
    Language language,
    String driverCode,
    String userCode,
    String solutionCode
) {}
