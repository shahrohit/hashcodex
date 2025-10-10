package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.ProblemAdapter;
import com.shahrohit.hashcodex.entities.Problem;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.ProblemRepository;
import com.shahrohit.hashcodex.repositories.ProblemTestcaseRepository;
import com.shahrohit.hashcodex.dtos.requests.CreateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem;
import com.shahrohit.hashcodex.services.AdminProblemTestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProblemTestcaseServiceImpl implements AdminProblemTestcaseService {
    private final ProblemRepository problemRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;

    @Override
    public Long create(Integer problemNumber, CreateTestcaseRequest body) {
        Long id = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        Problem problem = problemRepository.getReferenceById(id);
        return problemTestcaseRepository.save(ProblemAdapter.toEntity(body, problem)).getId();
    }

    @Override
    public Page<AdminTestcaseItem> findTestcases(Integer problemNumber, Pageable pageable) {
        Long id = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        return problemTestcaseRepository.findTestcasesByProblemId(id, pageable);
    }

    @Override
    @Transactional
    public void updateInput(Integer problemNumber, Long testcaseId, UpdateTestcaseRequest body) {
        if (!problemTestcaseRepository.existsByIdAndProblemNumber(testcaseId, problemNumber)) {
            throw new NotFoundException(ErrorCode.TESTCASE_NOT_FOUND);
        }

        problemTestcaseRepository.updateInputById(testcaseId, body.content());
    }

    @Override
    @Transactional
    public void updateOutput(Integer problemNumber, Long testcaseId, UpdateTestcaseRequest body) {
        if (!problemTestcaseRepository.existsByIdAndProblemNumber(testcaseId, problemNumber)) {
            throw new NotFoundException(ErrorCode.TESTCASE_NOT_FOUND);
        }

        problemTestcaseRepository.updateOutputById(testcaseId, body.content());
    }

    @Override
    @Transactional
    public void updateSample(Integer problemNumber, Long testcaseId, Boolean sample) {
        if (!problemTestcaseRepository.existsByIdAndProblemNumber(testcaseId, problemNumber)) {
            throw new NotFoundException(ErrorCode.TESTCASE_NOT_FOUND);
        }

        problemTestcaseRepository.updateSampleById(testcaseId, sample);
    }

    @Override
    @Transactional
    public void deleteTestcase(Integer problemNumber, Long testcaseId) {
        int deleteCount = problemTestcaseRepository.deleteByIdAndProblemNumber(testcaseId, problemNumber);
        if (deleteCount == 0) {
            throw new NotFoundException(ErrorCode.TESTCASE_NOT_FOUND);
        }
    }
}
