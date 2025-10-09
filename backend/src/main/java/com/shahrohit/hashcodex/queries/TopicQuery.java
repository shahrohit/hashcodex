package com.shahrohit.hashcodex.queries;

public interface TopicQuery {

    // language=JPQL
    String ADMIN_FIND_ID_BY_SLUG = "SELECT t.id FROM Topic t where t.slug = :slug";

    // language=JPQL
    String ADMIN_UPDATE_BY_SLUG = "UPDATE Topic t SET t.slug = :slug, t.name  = :name WHERE t.slug = :oldSlug";

    // language=JPQL
    String ADMIN_DELETE_BY_SLUG = "DELETE Topic t WHERE t.slug = :slug";


    // language=JPQL
    String PUBLIC_FIND_ALL = "SELECT new com.shahrohit.hashcodex.dtos.responses.TopicItem(t.slug, t.name) FROM Topic t ORDER BY t.id";

    // language=JPQL
    String PUBLIC_FIND_ALL_WITH_TOTAL_PROBLEMS = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.StudentTopicItem(t.slug, t.name, COUNT(DISTINCT p))
        FROM Topic t
        LEFT JOIN ProblemTopic pt ON pt.topic = t
        JOIN pt.problem p
        where p.active = true
        GROUP BY t.id, t.slug
        ORDER BY t.id
        """;


}
