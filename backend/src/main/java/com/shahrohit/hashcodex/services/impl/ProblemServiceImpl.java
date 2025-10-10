package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.dtos.responses.*;
import com.shahrohit.hashcodex.entities.Problem;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.UserProblemStatus;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.*;
import com.shahrohit.hashcodex.services.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ProblemService} to get the problems.
 */
@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final TopicRepository topicRepository;
    private final ProblemSubmissionRepository problemSubmissionRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final ProblemCodeRepository problemCodeRepository;
    private final ProblemTopicRepository problemTopicRepository;

    @Override
    public Page<UserProblemItem> findProblems(Long useId, Pageable pageable){
        if(useId == null) return problemRepository.findPublicProblems(pageable);
        return problemRepository.findUserProblems(useId, pageable);
    }

    @Override
    public List<TopicItem> findAllTopics() {
        return topicRepository.findAllTopics();
    }

    @Override
    public ProblemDetail findProblemBySlug(Long userId, String slug) {
        Problem problem = problemRepository.findActiveProblemBySlug(slug)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        List<String> topics = problemTopicRepository.findTopicNamesByProblemId(problem.getId());
        List<String> testcases = problemTestcaseRepository.findSampleTestcasesByProblemId(problem.getId());
        Map<Language, String> code = problemCodeRepository.findStarterCodeByProblemId(problem.getId())
            .stream()
            .collect(Collectors.toMap(
                r -> (Language) r[0],
                r -> ((String) r[1])
                )
            );

        UserProblemStatus status = UserProblemStatus.NONE;
        if(userId != null){
            if (problemSubmissionRepository.existsProblemSolved(userId, problem.getId())) {
                status = UserProblemStatus.SOLVED;
            } else if (problemSubmissionRepository.existProblemAttempted(userId, problem.getId())) {
                status = UserProblemStatus.ATTEMPTED;
            }
        }

        return new ProblemDetail(
            problem.getNumber(),
            problem.getTitle(),
            problem.getSlug(),
            problem.getDifficulty(),
            problem.getDescription(),
            problem.getParams(),
            topics,
            status,
            testcases,
            code
        );

    }

}
