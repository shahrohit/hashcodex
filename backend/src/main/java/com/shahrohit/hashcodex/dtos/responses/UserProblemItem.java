package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import com.shahrohit.hashcodex.enums.UserProblemStatus;

public record UserProblemItem(
    int number,
    String slug,
    String title,
    ProblemDifficulty difficulty,
    UserProblemStatus status
) {
}
