package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.ProblemDifficulty;

import java.time.Instant;

public record AdminProblemItem(
    Integer number,
    String slug,
    String title,
    ProblemDifficulty difficulty,
    Boolean active,
    Instant updatedAt
) {}
