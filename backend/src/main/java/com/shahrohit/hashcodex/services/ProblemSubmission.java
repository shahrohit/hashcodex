package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.requests.RunCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.SubmitCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.SubmissionItem;

import java.util.List;

public interface ProblemSubmission {
    String runCode(Long userId, Integer problemNumber, RunCodeRequest body);
    String submitCode(Long userId, Integer number, SubmitCodeRequest body);
    List<SubmissionItem> findAllSubmissions(Long userId, Integer number);
}
