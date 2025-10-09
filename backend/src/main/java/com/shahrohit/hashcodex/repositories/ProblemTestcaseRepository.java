package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.dtos.TestcaseDto;
import com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem;
import com.shahrohit.hashcodex.entities.ProblemTestcase;
import com.shahrohit.hashcodex.queries.ProblemQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemTestcaseRepository extends JpaRepository<ProblemTestcase, Long> {

    @Query(ProblemQuery.ADMIN_EXISTS_TESTCASE)
    boolean existsByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );

    @Query(ProblemQuery.ADMIN_PAGE_TESTCASES)
    Page<AdminTestcaseItem> findTestcasesByProblemId(@Param("problemId") Long problemId, Pageable pageable);


    @Query(ProblemQuery.USER_FIND_SAMPLE_TESTCASES)
    List<String> findSampleTestcasesByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemQuery.USER_FIND_RUN_TESTCASES)
    List<TestcaseDto> findRunTestcasesByProblemId(@Param("problemId") Long problemId);

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_TESTCASE_INPUT)
    void updateInputById(
        @Param("id") Long id,
        @Param("input") String input
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_TESTCASE_OUTPUT)
    void updateOutputById(
        @Param("id") Long id,
        @Param("output") String output
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_TESTCASE_SAMPLE)
    void updateSampleById(
        @Param("id") Long id,
        @Param("sample") Boolean sample
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_DELETE_TESTCASE)
    int deleteByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );
}
