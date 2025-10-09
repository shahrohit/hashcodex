package com.shahrohit.hashcodex.queues;


import com.shahrohit.hashcodex.enums.SubmissionType;

import java.util.List;

public record SubmissionResult(
    Long id,
    int total,
    int passed,
    String status,
    String compileError,
    long timeMs,
    List<CaseDto> cases,
    String errorMessage,
    SubmissionType submissionType
) {
}
