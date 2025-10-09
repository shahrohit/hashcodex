package com.shahrohit.hashcodex.adapters;

import com.shahrohit.hashcodex.entities.Topic;
import com.shahrohit.hashcodex.dtos.requests.CreateTopicRequest;

public class TopicAdapter {
    public static Topic toEntity(CreateTopicRequest body) {
        Topic topic = new Topic();
        topic.setSlug(body.slug());
        topic.setName(body.name());
        return topic;
    }
}
