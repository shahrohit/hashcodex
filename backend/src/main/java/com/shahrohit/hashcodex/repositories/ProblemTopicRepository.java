package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.entities.ProblemTopic;
import com.shahrohit.hashcodex.entities.ProblemTopicId;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import com.shahrohit.hashcodex.queries.TopicQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemTopicRepository extends JpaRepository<ProblemTopic, ProblemTopicId> {
    @Query(TopicQuery.EXT_TOPIC_BY_PROBLEM_ID)
    boolean existsTopicByProblemId(Long problemId);

    @Query(TopicQuery.FIND_TOPIC_NAMES_BY_ID)
    List<String> findTopicNamesByProblemId(@Param("problemId") Long problemId);

    @Query(TopicQuery.FIND_ALL_TOPICS_BY_PROBLEM_NUM)
    List<TopicItem> findAllTopicsByProblemNumber(@Param("number") Integer problemNumber);

    @Modifying
    @Query(TopicQuery.DEL_PROBLEM_TOPIC_BY_IDS)
    void deleteProblemTopicById(
        @Param("problemId") Long problemId,
        @Param("topicId") Long topicId
    );

}
