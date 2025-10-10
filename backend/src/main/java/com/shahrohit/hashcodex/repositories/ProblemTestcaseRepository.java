package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.dtos.TestcaseDto;
import com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem;
import com.shahrohit.hashcodex.entities.ProblemTestcase;
import com.shahrohit.hashcodex.queries.ProblemTestcaseQuery;
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
    @Query(ProblemTestcaseQuery.EXT_TCASE_BY_ID_PROBLEM_NUM)
    boolean existsByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );

    @Query(ProblemTestcaseQuery.EXT_TCASES_BY_PROBLEM_ID)
    boolean existsProblemTestcases(@Param("problemId") Long problemId);

    @Query(ProblemTestcaseQuery.ADMIN_PAGE_TCASES)
    Page<AdminTestcaseItem> findTestcasesByProblemId(
        @Param("problemId") Long problemId,
        Pageable pageable
    );

    @Query(ProblemTestcaseQuery.FIND_SAMPLE_TCASES)
    List<String> findSampleTestcasesByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemTestcaseQuery.FIND_RUN_TCASES)
    List<TestcaseDto> findRunTestcasesByProblemId(@Param("problemId") Long problemId);

    @Modifying
    @Query(ProblemTestcaseQuery.UP_TCASE_INPUT)
    void updateInputById(
        @Param("id") Long id,
        @Param("input") String input
    );

    @Modifying
    @Query(ProblemTestcaseQuery.UP_TCASE_OUTPUT)
    void updateOutputById(
        @Param("id") Long id,
        @Param("output") String output
    );

    @Modifying
    @Query(ProblemTestcaseQuery.UP_TCASE_SAMPLE)
    void updateSampleById(
        @Param("id") Long id,
        @Param("sample") Boolean sample
    );

    @Modifying
    @Query(ProblemTestcaseQuery.DEL_TCASE_BY_ID_AND_PROBLEM_NUM)
    int deleteByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );
}
