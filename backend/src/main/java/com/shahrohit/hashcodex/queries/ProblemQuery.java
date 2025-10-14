package com.shahrohit.hashcodex.queries;

public interface ProblemQuery {
    // language=JPQL
    String ADMIN_PAGE_ALL = "SELECT new com.shahrohit.hashcodex.dtos.responses.AdminProblemItem(p.number, p.slug, p.title, p.difficulty, p.active, p.updatedAt) FROM Problem p";

    // language=JPQL
    String ADMIN_PAGE_ALL_BY_QUERY = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminProblemItem(p.number, p.slug, p.title, p.difficulty, p.active, p.updatedAt)
        FROM Problem p
        WHERE (:isNumeric = true AND (p.number = :number OR LOWER(p.title) like CONCAT('%', :title, '%')))
        OR (:isNumeric = false AND LOWER(p.title) LIKE CONCAT('%', :title, '%'))
        """;

    // language=JPQL
    String FIND_ID_BY_NUM = "SELECT p.id FROM Problem p WHERE p.number = :number";


    // language=JPQL
    String ADMIN_FIND_BY_SLUG = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail
        (p.number, p.slug, p.title, p.description, p.difficulty, p.active, p.params, p.createdAt, p.updatedAt)
        FROM Problem p WHERE  p.slug = :slug
        """;


    // language=JPQL
    String UP_BASIC_BY_NUM = """
        UPDATE Problem p
        SET p.title = :title, p.difficulty = :difficulty, p.params = :params, p.updatedAt = :updatedAt
        WHERE p.number = :number
        """;
    // language=JPQL
    String UP_DESC_BY_NUM = "UPDATE Problem p SET p.description = :description, p.updatedAt = :updatedAt  WHERE p.number = :number";

    // language=JPQL
    String UP_SLUG_BY_NUM = "UPDATE Problem p SET p.slug = :slug, p.updatedAt = :updatedAt WHERE p.number = :number";

    // language=JPQL
    String UP_ACTIVE_BY_ID = "UPDATE Problem p SET p.active = :active, p.updatedAt = :updatedAt WHERE p.id = :id";

    // language=JPQL
    String PUBLIC_PAGE_PROBLEMS = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.UserProblemItem(p.number, p.slug, p.title, p.difficulty, com.shahrohit.hashcodex.enums.UserProblemStatus.NONE)
        FROM Problem p WHERE p.active = TRUE ORDER BY p.id
        """;

    // language=JPQL
    String USER_PAGE_PROBLEMS = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.UserProblemItem(p.number, p.slug, p.title, p.difficulty,
            CASE
                WHEN COUNT(ps) = 0 THEN com.shahrohit.hashcodex.enums.UserProblemStatus.NONE
                WHEN SUM(CASE WHEN ps.status = 'SOLVED' THEN 1 ELSE 0 END) > 0 THEN com.shahrohit.hashcodex.enums.UserProblemStatus.SOLVED
                ELSE com.shahrohit.hashcodex.enums.UserProblemStatus.ATTEMPTED
            END
        )
        FROM Problem p
        LEFT JOIN ProblemSubmission ps on p.id = ps.problem.id AND ps.user.id = :userId
        WHERE p.active = TRUE
        GROUP BY p.id
        ORDER BY p.id
        """;

    // language=JPQL
    String FIND_ACTIVE_PROBLEM_BY_SLUG = "SELECT p FROM Problem p WHERE p.slug = :slug AND p.active = true";

    // language=JPQL
    String FIND_ID_AND_TIME_LIMIT_BY_ACTIVE_NUM = "SELECT new com.shahrohit.hashcodex.dtos.ProblemIdAndTimeLimit(p.id, p.timeLimit) FROM Problem p WHERE p.number = :number AND p.active = true";
}
