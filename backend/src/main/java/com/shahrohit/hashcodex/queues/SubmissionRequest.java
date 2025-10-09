package com.shahrohit.hashcodex.queues;

import com.shahrohit.hashcodex.dtos.TestcaseDto;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.SubmissionType;

import java.util.List;

public record SubmissionRequest(
    Long submissionId,
    Language language,
    String solutionCode,
    String code,
    int startLine,
    List<TestcaseDto> testcases,
    SubmissionType submissionType
) {

    public static SubmissionRequest runRequest(Language language, String solutionCode, String code, int startLine, List<TestcaseDto> testcases) {
        return new SubmissionRequest(
            null, language, solutionCode, code, startLine, testcases, SubmissionType.RUN
        );
    }

    public static SubmissionRequest submitRequest(Long id, Language language, String code, int startLine, List<TestcaseDto> testcases) {
        return new SubmissionRequest(
            id, language, null, code, startLine, testcases, SubmissionType.SUBMIT
        );
    }

}
