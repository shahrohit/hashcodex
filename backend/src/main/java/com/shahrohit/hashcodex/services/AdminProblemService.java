package com.shahrohit.hashcodex.services;


import com.shahrohit.hashcodex.dtos.requests.CreateProblemRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemBasicRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemDescriptionRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateSlugRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemItem;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminProblemService {
    void create(CreateProblemRequest body);
    Page<AdminProblemItem> findAll(String query, Pageable pageable);
    AdminProblemDetail findProblemDetail(String slug);
    List<TopicItem> findProblemTopics(Integer problemNumber);
    void updateBasicDetail(Integer problemNumber, UpdateProblemBasicRequest body);
    void updateDescription(Integer problemNumber, UpdateProblemDescriptionRequest body);
    String updateSlug(Integer problemNumber, UpdateSlugRequest body);
    void updateActive(Integer problemNumber, Boolean active);
    void addTopic(Integer problemNumber, String topicSlug);
    void removeTopic(Integer problemNumber, String topicSlug);
}
