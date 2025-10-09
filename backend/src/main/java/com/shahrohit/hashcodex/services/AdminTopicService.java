package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.requests.CreateTopicRequest;

public interface AdminTopicService {
    void create(CreateTopicRequest body);
    void updateBySlug(String oldSlug, CreateTopicRequest body);
    void deleteBySlug(String slug);
}
