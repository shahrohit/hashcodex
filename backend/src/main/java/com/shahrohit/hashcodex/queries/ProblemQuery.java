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
    String ADMIN_FIND_ID_BY_NUMBER = "SELECT p.id FROM Problem p WHERE p.number = :number";


    // language=JPQL
    String ADMIN_FIND_BY_SLUG = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail
        (p.number, p.slug, p.title, p.description, p.difficulty, p.active, p.params, p.createdAt, p.updatedAt)
        FROM Problem p WHERE  p.slug = :slug
        """;


    // language=JPQL
    String ADMIN_UPDATE_BASIC_BY_NUMBER = """
        UPDATE Problem p
        SET p.title = :title, p.difficulty = :difficulty, p.params = :params, p.updatedAt = :updatedAt
        WHERE p.number = :number
        """;
    // language=JPQL
    String ADMIN_UPDATE_DESCRIPTION_BY_NUMBER = "UPDATE Problem p SET p.description = :description, p.updatedAt = :updatedAt  WHERE p.number = :number";

    // language=JPQL
    String ADMIN_UPDATE_SLUG_BY_NUMBER = "UPDATE Problem p SET p.slug = :slug, p.updatedAt = :updatedAt WHERE p.number = :number";

    // language=JPQL
    String ADMIN_UPDATE_ACTIVE = "UPDATE Problem p SET p.active = :active, p.updatedAt = :updatedAt WHERE p.id = :id";

    // language=JPQL
    String ADMIN_EXISTS_TOPIC_BY_PROBLEM_ID = "SELECT COUNT(pt) > 0 FROM ProblemTopic pt WHERE pt.problem.id = :problemId";
    // language=JPQL
    String ADMIN_FIND_TOPICS_BY_PROBLEM_NUMBER = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.TopicItem(t.slug, t.name)
        FROM ProblemTopic pt
        JOIN pt.topic t
        WHERE pt.problem.number = :number
        """;

    // language=JPQL
    String ADMIN_DELETE_PROBLEM_TOPIC_BY_ID = """
          DELETE FROM ProblemTopic pt
          where pt.problem.id = :problemId and pt.topic.id = :topicId
        """;

    // ------ TESTCASES ------
    // language=JPQL
    String ADMIN_EXISTS_TESTCASES_BY_PROBLEM_ID = "SELECT COUNT(ptc) > 0 FROM ProblemTestcase ptc WHERE ptc.problem.id = :problemId";

    // language=JPQL
    String ADMIN_PAGE_TESTCASES = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem(ptc.id, ptc.input, ptc.output, ptc.sample)
        FROM ProblemTestcase ptc WHERE ptc.problem.id = :problemId
        """;

    // language=JPQL
    String ADMIN_EXISTS_TESTCASE = "SELECT COUNT(ptc) > 0 FROM ProblemTestcase ptc WHERE ptc.id = :id and ptc.problem.number = :number";

    // language=JPQL
    String ADMIN_UPDATE_TESTCASE_INPUT = "UPDATE ProblemTestcase ptc SET ptc.input = :input WHERE ptc.id = :id";

    // language=JPQL
    String ADMIN_UPDATE_TESTCASE_OUTPUT = "UPDATE ProblemTestcase ptc SET ptc.output = :output WHERE ptc.id = :id";

    // language=JPQL
    String ADMIN_UPDATE_TESTCASE_SAMPLE = "UPDATE ProblemTestcase ptc SET ptc.sample = :sample WHERE ptc.id = :id";

    // language=JPQL
    String ADMIN_DELETE_TESTCASE = "DELETE ProblemTestcase ptc WHERE ptc.id = :id and ptc.problem.number = :number";

    // ------ Code ------
    // language=JPQL
    String ADMIN_EXISTS_CODES_BY_PROBLEM_ID = """
        SELECT (COUNT(DISTINCT pc.language) = :langCount)
        FROM ProblemCode pc
        WHERE pc.problem.id = :problemId
        """;

    // language=JPQL
    String ADMIN_EXISTS_CODE = """
        SELECT CASE WHEN COUNT(pc) > 0 THEN TRUE ELSE FALSE END
        FROM ProblemCode pc WHERE pc.id = :id and pc.problem.number = :number
        """;

    // language=JPQL
    String ADMIN_FIND_ALL_CODES = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminCodeItem(pc.id, pc.language, pc.driverCode, pc.userCode, pc.solutionCode)
        FROM ProblemCode pc WHERE pc.problem.id = :problemId
        """;

    // language=JPQL
    String ADMIN_UPDATE_DRIVER_CODE = "UPDATE ProblemCode pc SET pc.driverCode = :code WHERE pc.id = :id";

    // language=JPQL
    String ADMIN_UPDATE_USER_CODE = "UPDATE ProblemCode pc SET pc.userCode = :code WHERE pc.id = :id";

    // language=JPQL
    String ADMIN_UPDATE_SOLUTION_CODE = "UPDATE ProblemCode pc SET pc.solutionCode = :code WHERE pc.id = :id";


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
    String USER_FIND_ACTIVE_PROBLEM_BY_SLUG = "SELECT p FROM Problem p WHERE p.slug = :slug AND p.active = true";

    // language=JPQL
    String USER_FIND_ID_BY_NUMBER = "SELECT p.id FROM Problem p WHERE p.number = :number AND p.active = true";


    // language=JPQL
    String USER_EXIST_PROBLEM_SOLVED = """
            SELECT (COUNT(ps) > 0)
            FROM ProblemSubmission ps
            WHERE ps.problem.id = :problemId
              AND ps.user.id = :userId
              AND ps.status = com.shahrohit.hashcodex.enums.ProblemSubmissionStatus.SOLVED
        """;

    // language=JPQL
    String USER_EXIST_PROBLEM_ATTEMPTED = """
            SELECT (COUNT(ps) > 0)
            FROM ProblemSubmission ps
            WHERE ps.problem.id = :problemId
              AND ps.user.id = :userId
        """;

    // language=JPQL
    String USER_FIND_SUBMISSIONS = """
            SELECT new com.shahrohit.hashcodex.dtos.responses.SubmissionItem(ps.status, ps.language, ps.submittedAt)
            FROM ProblemSubmission ps
            WHERE ps.user.id = :userId AND ps.problem.number = :number
             AND ps.status <> com.shahrohit.hashcodex.enums.ProblemSubmissionStatus.PENDING
            ORDER BY ps.id desc
        """;

    // language=JPQL
    String USER_UPDATE_SUBMISSION_STATUS = "UPDATE ProblemSubmission ps SET ps.status = :status WHERE ps.id = :id";


    // language=JPQL
    String USER_FIND_TOPIC_NAMES_BY_ID = """
        SELECT t.name
        FROM ProblemTopic pt
        JOIN pt.topic t
        WHERE pt.problem.id = :problemId
        """;

    // language=JPQL
    String USER_FIND_SAMPLE_TESTCASES = """
        SELECT ptc.input
        FROM ProblemTestcase ptc
        WHERE ptc.problem.id = :problemId
        AND ptc.sample = true
        ORDER BY ptc.id
        """;

    // language=JPQL
    String USER_FIND_RUN_TESTCASES = """
        SELECT new com.shahrohit.hashcodex.dtos.TestcaseDto(ptc.input, ptc.output)
        FROM ProblemTestcase ptc
        WHERE ptc.problem.id = :problemId
        ORDER BY ptc.id
        """;

    // language=JPQL
    String USER_FIND_STARTER_CODE = """
        SELECT pc.language, pc.userCode
        FROM ProblemCode pc
        WHERE pc.problem.id = :problemId
        """;

    // language=JPQL
    String USER_FIND_DRIVER_CODE = """
        SELECT pc.driverCode
        FROM ProblemCode pc
        WHERE pc.problem.id = :problemId AND pc.language = :language
        """;

    // language=JPQL
    String USER_FIND_RUN_CODE = """
        SELECT new com.shahrohit.hashcodex.dtos.RunCodeDto(pc.driverCode, pc.solutionCode)
        FROM ProblemCode pc
        WHERE pc.problem.id = :problemId AND pc.language = :language
        """;


}
