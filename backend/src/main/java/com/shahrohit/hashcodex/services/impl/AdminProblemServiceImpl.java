package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.ProblemAdapter;
import com.shahrohit.hashcodex.dtos.requests.CreateProblemRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemBasicRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemDescriptionRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateSlugRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemItem;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import com.shahrohit.hashcodex.entities.Problem;
import com.shahrohit.hashcodex.entities.ProblemTopic;
import com.shahrohit.hashcodex.entities.Topic;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.exceptions.AlreadyExistsException;
import com.shahrohit.hashcodex.exceptions.ForbiddenException;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.*;
import com.shahrohit.hashcodex.services.AdminProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProblemServiceImpl implements AdminProblemService {
    private final ProblemRepository problemRepository;
    private final TopicRepository topicRepository;
    private final ProblemTopicRepository problemTopicRepository;
    private final ProblemTestcaseRepository problemTestcaseRepository;
    private final ProblemCodeRepository problemCodeRepository;

    private static final int languageCount = Language.values().length;

    @Override
    public void create(CreateProblemRequest body) {
        if (problemRepository.existsBySlug(body.slug())) {
            throw new AlreadyExistsException(ErrorCode.PROBLEM_ALREADY_EXISTS);
        }

        Integer problemNumber = Math.toIntExact(problemRepository.count()) + 1;
        problemRepository.save(ProblemAdapter.toEntity(problemNumber, body));
    }

    @Override
    public Page<AdminProblemItem> findAll(String query, Pageable pageable) {
        String searchQuery = query == null ? null : query.trim();
        if (searchQuery == null || searchQuery.isEmpty()) {
            return problemRepository.findAllProblems(pageable);
        }

        boolean isNumeric = query.chars().allMatch(Character::isDigit);
        int number = isNumeric ? Integer.parseInt(query) : 0;

        return problemRepository.findAllProblemsByQuery(isNumeric, number, isNumeric ? searchQuery : searchQuery.toLowerCase(), pageable);

    }

    @Override
    public AdminProblemDetail findProblemDetail(String slug) {
        return problemRepository.findProblemDetailBySlug(slug)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    @Override
    public List<TopicItem> findProblemTopics(Integer problemNumber) {
        return problemTopicRepository.findAllTopicsByProblemNumber(problemNumber);
    }

    @Override
    @Transactional
    public void updateBasicDetail(Integer problemNumber, UpdateProblemBasicRequest body) {
        int updateCount = problemRepository.updateBasicByNumber(problemNumber, body.title(), body.difficulty(), body.params(), Instant.now());
        if (updateCount == 0) throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
    }

    @Override
    @Transactional
    public void updateDescription(Integer problemNumber, UpdateProblemDescriptionRequest body) {
        if (!problemRepository.existsByNumber(problemNumber)) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        }
        problemRepository.updateDescriptionByNumber(problemNumber, body.description(), Instant.now());
    }

    @Override
    @Transactional
    public String updateSlug(Integer problemNumber, UpdateSlugRequest body) {
        if(problemRepository.existsBySlug(body.slug())) {
            throw new AlreadyExistsException(ErrorCode.PROBLEM_ALREADY_EXISTS);
        }

        int updateCount = problemRepository.updateSlugByNumber(problemNumber, body.slug(), Instant.now());
        if (updateCount == 0) throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        return body.slug();
    }

    @Override
    @Transactional
    public void updateActive(Integer problemNumber, Boolean active) {
        Long problemId = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        if (active) {
            if (!problemTopicRepository.existsTopicByProblemId(problemId))
                throw new ForbiddenException(ErrorCode.TOPIC_REQUIRED);
            if (!problemCodeRepository.existsProblemCodes(problemId, languageCount))
                throw new ForbiddenException(ErrorCode.CODE_REQUIRED);
            if (!problemTestcaseRepository.existsProblemTestcases(problemId))
                throw new ForbiddenException(ErrorCode.TESTCASE_REQUIRED);
        }

        problemRepository.updateActiveById(problemId, active, Instant.now());
    }


    // ------ Topic ----------
    @Override
    public void addTopic(Integer problemNumber, String topicSlug) {
        Long problemId = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        Long topicId = topicRepository.findIdBySlug(topicSlug)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TOPIC_NOT_FOUND));

        Problem problem = problemRepository.getReferenceById(problemId);
        Topic topic = topicRepository.getReferenceById(topicId);

        ProblemTopic problemTopic = ProblemAdapter.toEntity(problem, topic);
        problemTopicRepository.save(problemTopic);
    }

    @Override
    @Transactional
    public void removeTopic(Integer problemNumber, String topicSlug) {
        Long problemId = problemRepository.findIdByNumber(problemNumber)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));

        Long topicId = topicRepository.findIdBySlug(topicSlug)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TOPIC_NOT_FOUND));

        problemTopicRepository.deleteProblemTopicById(problemId, topicId);
    }

}
