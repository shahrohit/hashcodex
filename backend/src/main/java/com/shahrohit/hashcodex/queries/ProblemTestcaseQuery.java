package com.shahrohit.hashcodex.queries;

public interface ProblemTestcaseQuery {
    // language=JPQL
    String EXT_TCASE_BY_ID_PROBLEM_NUM = "SELECT COUNT(ptc) > 0 FROM ProblemTestcase ptc WHERE ptc.id = :id and ptc.problem.number = :number";

    // language=JPQL
    String EXT_TCASES_BY_PROBLEM_ID = "SELECT COUNT(ptc) > 0 FROM ProblemTestcase ptc WHERE ptc.problem.id = :problemId";

    // language=JPQL
    String ADMIN_PAGE_TCASES = """
        SELECT new com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem(ptc.id, ptc.input, ptc.output, ptc.sample)
        FROM ProblemTestcase ptc WHERE ptc.problem.id = :problemId
        """;

    // language=JPQL
    String FIND_SAMPLE_TCASES = """
        SELECT ptc.input
        FROM ProblemTestcase ptc
        WHERE ptc.problem.id = :problemId
        AND ptc.sample = true
        ORDER BY ptc.id
        """;

    // language=JPQL
    String FIND_RUN_TCASES = """
        SELECT new com.shahrohit.hashcodex.dtos.TestcaseDto(ptc.input, ptc.output)
        FROM ProblemTestcase ptc
        WHERE ptc.problem.id = :problemId
        ORDER BY ptc.id
        """;

    // language=JPQL
    String UP_TCASE_INPUT = "UPDATE ProblemTestcase ptc SET ptc.input = :input WHERE ptc.id = :id";

    // language=JPQL
    String UP_TCASE_OUTPUT = "UPDATE ProblemTestcase ptc SET ptc.output = :output WHERE ptc.id = :id";

    // language=JPQL
    String UP_TCASE_SAMPLE = "UPDATE ProblemTestcase ptc SET ptc.sample = :sample WHERE ptc.id = :id";

    // language=JPQL
    String DEL_TCASE_BY_ID_AND_PROBLEM_NUM = "DELETE ProblemTestcase ptc WHERE ptc.id = :id and ptc.problem.number = :number";

}
