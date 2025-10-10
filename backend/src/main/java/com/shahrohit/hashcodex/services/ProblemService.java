package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.responses.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface to get the problems publically.
 */
public interface ProblemService {
    Page<UserProblemItem> findProblems(Long useId, Pageable pageable);
    List<TopicItem> findAllTopics();
    ProblemDetail findProblemBySlug(Long userId, String slug);
}
