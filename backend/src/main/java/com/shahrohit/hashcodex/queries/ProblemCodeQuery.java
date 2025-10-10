package com.shahrohit.hashcodex.queries;

public interface ProblemCodeQuery {
    // language=JPQL
    String EXT_ALL_LANG_CODES_BY_PROBLEM_ID = """
        SELECT (COUNT(DISTINCT pc.language) = :langCount)
        FROM ProblemCode pc
        WHERE pc.problem.id = :problemId
        """;

    // language=JPQL
    String EXT_CODE_BY_PROBLEM_ID_NUM = """
        SELECT CASE WHEN COUNT(pc) > 0 THEN TRUE ELSE FALSE END
        FROM ProblemCode pc WHERE pc.id = :id and pc.problem.number = :number
        """;

    // language=JPQL
    String ADMIN_FIND_ALL_CODES = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminCodeItem(pc.id, pc.language, pc.driverCode, pc.userCode, pc.solutionCode)
        FROM ProblemCode pc WHERE pc.problem.id = :problemId
        """;

    // language=JPQL
    String UP_DRIVER_CODE = "UPDATE ProblemCode pc SET pc.driverCode = :code WHERE pc.id = :id";

    // language=JPQL
    String UP_USER_CODE = "UPDATE ProblemCode pc SET pc.userCode = :code WHERE pc.id = :id";

    // language=JPQL
    String UP_SOLUTION_CODE = "UPDATE ProblemCode pc SET pc.solutionCode = :code WHERE pc.id = :id";


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
