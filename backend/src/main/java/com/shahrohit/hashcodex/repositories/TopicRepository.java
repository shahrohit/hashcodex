package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import com.shahrohit.hashcodex.entities.Topic;
import com.shahrohit.hashcodex.queries.TopicQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsBySlug(String slug);

    @Query(TopicQuery.ADMIN_FIND_ID_BY_SLUG)
    Optional<Long> findIdBySlug(String slug);

    @Query(TopicQuery.PUBLIC_FIND_ALL)
    List<TopicItem> findAllTopics();

    @Modifying
    @Query(TopicQuery.ADMIN_UPDATE_BY_SLUG)
    int updateBySlug(
        @Param("oldSlug") String oldSlug,
        @Param("slug") String slug,
        @Param("name") String name
    );

    @Modifying
    @Query(TopicQuery.ADMIN_DELETE_BY_SLUG)
    int deleteBySlug(@Param("slug") String slug);

}
