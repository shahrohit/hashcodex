package com.shahrohit.hashcodex.dtos.responses;

import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.ProblemSubmissionStatus;

import java.time.Instant;

public record SubmissionItem(
    ProblemSubmissionStatus status,
    Language language,
    Instant submittedAt
) {}
