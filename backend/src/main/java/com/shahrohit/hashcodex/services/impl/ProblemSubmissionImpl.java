package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.ProblemAdapter;
import com.shahrohit.hashcodex.dtos.RunCodeDto;
import com.shahrohit.hashcodex.dtos.TestcaseDto;
import com.shahrohit.hashcodex.dtos.requests.RunCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.SubmitCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.SubmissionItem;
import com.shahrohit.hashcodex.exceptions.ForbiddenException;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.queues.SubmissionProducer;
import com.shahrohit.hashcodex.queues.SubmissionRequest;
import com.shahrohit.hashcodex.repositories.ProblemCodeRepository;
import com.shahrohit.hashcodex.repositories.ProblemRepository;
import com.shahrohit.hashcodex.repositories.ProblemSubmissionRepository;
import com.shahrohit.hashcodex.repositories.ProblemTestcaseRepository;
import com.shahrohit.hashcodex.services.ProblemSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProblemSubmissionImpl implements ProblemSubmission {
    private final ProblemRepository problemRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final ProblemCodeRepository problemCodeRepository;
    private final ProblemSubmissionRepository problemSubmissionRepository;
    private final SubmissionProducer submissionProducer;

    @Override
    public String runCode(Long userId, Integer number, RunCodeRequest body) {
        Long id = problemRepository.findIdByActiveNumber(number)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        RunCodeDto dto = problemCodeRepository.findRunCodeByProblemIdAndLanguage(id, body.language())
            .orElseThrow(() -> new ForbiddenException(ErrorCode.PROBLEM_NOT_FOUND));

        int startLine = findLineNumber(dto.driverCode());
        if (startLine == -1) {
            throw new ForbiddenException(ErrorCode.SERVER_ERROR);
        }

        List<TestcaseDto> testcases = body.testcases().stream()
            .map((item) -> new TestcaseDto(item, null))
            .toList();

        String solutionCode = mergeCode(dto.driverCode(), dto.solutionCode());
        String code = mergeCode(dto.driverCode(), body.code());

        SubmissionRequest request = SubmissionRequest.runRequest(body.language(), solutionCode, code, startLine,testcases);
        return submissionProducer.send(request);
    }

    @Override
    public String submitCode(Long userId, Integer number, SubmitCodeRequest body) {
        Long id = problemRepository.findIdByActiveNumber(number)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        List<TestcaseDto> testcases = problemTestcaseRepository.findRunTestcasesByProblemId(id);

        String driverCode = problemCodeRepository.findDriverCodeByProblemIdAndLanguage(id, body.language())
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        int startLine = findLineNumber(driverCode);
        if (startLine == -1) { throw new ForbiddenException(ErrorCode.SERVER_ERROR);}
        String code = mergeCode(driverCode, body.code());

        com.shahrohit.hashcodex.entities.ProblemSubmission submission = problemSubmissionRepository.save(ProblemAdapter.toEntity(userId, id, body));
        SubmissionRequest request = SubmissionRequest.submitRequest(submission.getId(), body.language(), code, startLine, testcases);
        return submissionProducer.send(request);
    }

    @Override
    public List<SubmissionItem> findAllSubmissions(Long userId, Integer number) {
        return problemSubmissionRepository.findProblemSubmissionsByUser(userId, number);
    }

    public static int findLineNumber(String code) {
        int index = code.indexOf("{{code}}");
        if (index == -1) return -1; // not found

        int lineNumber = 1;
        for (int i = 0; i < index; i++) {
            if (code.charAt(i) == '\n') {
                lineNumber++;
            }
        }
        return lineNumber;
    }

    public static String mergeCode(String driverCode, String userCode) {
        return driverCode.replaceFirst(Pattern.quote("{{code}}"), userCode);
    }
}
