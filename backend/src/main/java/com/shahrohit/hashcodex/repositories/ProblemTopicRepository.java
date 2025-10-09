package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.entities.ProblemTopic;
import com.shahrohit.hashcodex.entities.ProblemTopicId;
import com.shahrohit.hashcodex.queries.ProblemQuery;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemTopicRepository extends JpaRepository<ProblemTopic, ProblemTopicId> {
    @Query(ProblemQuery.ADMIN_EXISTS_TOPIC_BY_PROBLEM_ID)
    boolean existsByProblemId(Long problemId);

    @Query(ProblemQuery.ADMIN_FIND_TOPICS_BY_PROBLEM_NUMBER)
    List<TopicItem> findAllTopicsByProblemNumber(@Param("number") Integer problemNumber);

    @Modifying
    @Query(ProblemQuery.ADMIN_DELETE_PROBLEM_TOPIC_BY_ID)
    void deleteProblemTopicById(@Param("problemId") Long problemId, @Param("topicId") Long topicId);

}
