package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.ProblemDifficulty;

import java.time.Instant;

public record AdminProblemDetail(
    Integer number,
    String slug,
    String title,
    String description,
    ProblemDifficulty difficulty,
    Boolean active,
    String params,
    Instant createdAt,
    Instant updatedAt
) {}
