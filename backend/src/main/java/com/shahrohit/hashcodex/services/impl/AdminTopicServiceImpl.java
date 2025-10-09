package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.TopicAdapter;
import com.shahrohit.hashcodex.exceptions.AlreadyExistsException;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.TopicRepository;
import com.shahrohit.hashcodex.dtos.requests.CreateTopicRequest;
import com.shahrohit.hashcodex.services.AdminTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminTopicServiceImpl implements AdminTopicService {
    private final TopicRepository topicRepository;

    @Override
    public void create(CreateTopicRequest body) {
        if (topicRepository.existsBySlug(body.slug())) {
            throw new AlreadyExistsException(ErrorCode.TOPIC_ALREADY_EXISTS);
        }
        topicRepository.save(TopicAdapter.toEntity(body));
    }

    @Override
    @Transactional
    public void updateBySlug(String oldSlug, CreateTopicRequest body) {
        if(!Objects.equals(oldSlug, body.slug()) && topicRepository.existsBySlug(body.slug())) {
            throw new AlreadyExistsException(ErrorCode.TOPIC_ALREADY_EXISTS);
        }
        int updateCount = topicRepository.updateBySlug(oldSlug, body.slug(), body.name());
        if (updateCount == 0) throw new NotFoundException(ErrorCode.TOPIC_NOT_FOUND);
    }

    @Override
    @Transactional
    public void deleteBySlug(String slug) {
        int deleteCount = topicRepository.deleteBySlug(slug);
        if (deleteCount == 0) throw new NotFoundException(ErrorCode.TOPIC_NOT_FOUND);
    }
}
