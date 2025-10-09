package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import com.shahrohit.hashcodex.enums.UserProblemStatus;

import java.util.List;
import java.util.Map;

public record ProblemDetail(
    Integer number,
    String title,
    String slug,
    ProblemDifficulty difficulty,
    String description,
    String params,
    List<String> topics,
    UserProblemStatus status,
    List<String> testcases,
    Map<Language, String> code
) {}
