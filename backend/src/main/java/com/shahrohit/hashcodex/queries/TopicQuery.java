package com.shahrohit.hashcodex.queries;

public interface TopicQuery {
    // language=JPQL
    String FIND_ALL_TOPICS = "SELECT new com.shahrohit.hashcodex.dtos.responses.TopicItem(t.slug, t.name) FROM Topic t ORDER BY t.id";

    // language=JPQL
    String FIND_ID_BY_SLUG = "SELECT t.id FROM Topic t where t.slug = :slug";

    // language=JPQL
    String UP_TOPIC_SLUG = "UPDATE Topic t SET t.slug = :slug, t.name  = :name WHERE t.slug = :oldSlug";

    // language=JPQL
    String DEL_TOPIC_BY_SLUG = "DELETE Topic t WHERE t.slug = :slug";

    // -------------------- Problem-Topic Query -----------------------------
    // language=JPQL
    String EXT_TOPIC_BY_PROBLEM_ID = "SELECT COUNT(pt) > 0 FROM ProblemTopic pt WHERE pt.problem.id = :problemId";

    // language=JPQL
    String FIND_TOPIC_NAMES_BY_ID = """
        SELECT t.name
        FROM ProblemTopic pt
        JOIN pt.topic t
        WHERE pt.problem.id = :problemId
        """;

    // language=JPQL
    String FIND_ALL_TOPICS_BY_PROBLEM_NUM = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.TopicItem(t.slug, t.name)
        FROM ProblemTopic pt
        JOIN pt.topic t
        WHERE pt.problem.number = :number
        """;

    // language=JPQL
    String DEL_PROBLEM_TOPIC_BY_IDS = """
          DELETE FROM ProblemTopic pt
          where pt.problem.id = :problemId and pt.topic.id = :topicId
        """;
}
