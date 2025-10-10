package com.shahrohit.hashcodex.queries;

public interface ProblemSubmissionQuery {
    // language=JPQL
    String EXT_PROBLEM_SOLVED = """
            SELECT (COUNT(ps) > 0)
            FROM ProblemSubmission ps
            WHERE ps.problem.id = :problemId
              AND ps.user.id = :userId
              AND ps.status = com.shahrohit.hashcodex.enums.ProblemSubmissionStatus.SOLVED
        """;

    // language=JPQL
    String EXT_PROBLEM_ATTEMPTED = """
            SELECT (COUNT(ps) > 0)
            FROM ProblemSubmission ps
            WHERE ps.problem.id = :problemId
              AND ps.user.id = :userId
        """;

    // language=JPQL
    String FIND_SUBMISSIONS = """
            SELECT new com.shahrohit.hashcodex.dtos.responses.SubmissionItem(ps.status, ps.language, ps.submittedAt)
            FROM ProblemSubmission ps
            WHERE ps.user.id = :userId AND ps.problem.number = :number
             AND ps.status <> com.shahrohit.hashcodex.enums.ProblemSubmissionStatus.PENDING
            ORDER BY ps.id desc
        """;

    // language=JPQL
    String UP_SUBMISSION_STATUS = "UPDATE ProblemSubmission ps SET ps.status = :status WHERE ps.id = :id";

}
