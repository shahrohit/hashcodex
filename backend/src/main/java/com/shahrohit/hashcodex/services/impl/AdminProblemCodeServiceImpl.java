package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.ProblemAdapter;
import com.shahrohit.hashcodex.entities.Problem;
import com.shahrohit.hashcodex.entities.ProblemCode;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.ProblemCodeRepository;
import com.shahrohit.hashcodex.repositories.ProblemRepository;
import com.shahrohit.hashcodex.dtos.requests.CreateCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminCodeItem;
import com.shahrohit.hashcodex.services.AdminProblemCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProblemCodeServiceImpl implements AdminProblemCodeService {
    private final ProblemRepository problemRepository;
    private final ProblemCodeRepository problemCodeRepository;

    // ------ CREATE ------
    @Override
    public Long create(Integer problemNumber, CreateCodeRequest body) {
        Long id = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        Problem problem = problemRepository.getReferenceById(id);
        ProblemCode code = problemCodeRepository.save(ProblemAdapter.toEntity(body, problem));
        return code.getId();
    }

    // ------ GET ------
    @Override
    public List<AdminCodeItem> findCodes(Integer problemNumber) {
        Long id = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        return problemCodeRepository.findCodesByProblemId(id);
    }

    // ------ UPDATE ------
    @Override
    @Transactional
    public void updateDriverCode(Integer problemNumber, Long codeId, UpdateCodeRequest body) {
        if (!problemCodeRepository.existsByIdAndProblemNumber(codeId, problemNumber)) {
            throw new NotFoundException(ErrorCode.CODE_NOT_FOUND);
        }

        problemCodeRepository.updateDriverCodeById(codeId, body.code());
    }

    @Override
    @Transactional
    public void updateUserCode(Integer problemNumber, Long codeId, UpdateCodeRequest body) {
        if (!problemCodeRepository.existsByIdAndProblemNumber(codeId, problemNumber)) {
            throw new NotFoundException(ErrorCode.CODE_NOT_FOUND);
        }

        problemCodeRepository.updateUserCodeById(codeId, body.code());
    }

    @Override
    @Transactional
    public void updateSolutionCode(Integer problemNumber, Long codeId, UpdateCodeRequest body) {
        if (!problemCodeRepository.existsByIdAndProblemNumber(codeId, problemNumber)) {
            throw new NotFoundException(ErrorCode.CODE_NOT_FOUND);
        }

        problemCodeRepository.updateSolutionCodeById(codeId, body.code());
    }

}
