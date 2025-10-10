package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.entities.ProblemCode;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.queries.ProblemCodeQuery;
import com.shahrohit.hashcodex.dtos.responses.AdminCodeItem;
import com.shahrohit.hashcodex.dtos.RunCodeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemCodeRepository extends JpaRepository<ProblemCode, Long> {
    @Query(ProblemCodeQuery.EXT_CODE_BY_PROBLEM_ID_NUM)
    boolean existsByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );

    @Query(ProblemCodeQuery.EXT_ALL_LANG_CODES_BY_PROBLEM_ID)
    boolean existsProblemCodes(
        @Param("problemId") Long problemId,
        @Param("langCount") long langCount
    );


    @Query(ProblemCodeQuery.ADMIN_FIND_ALL_CODES)
    List<AdminCodeItem> findCodesByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemCodeQuery.USER_FIND_STARTER_CODE)
    List<Object[]> findStarterCodeByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemCodeQuery.USER_FIND_DRIVER_CODE)
    Optional<String> findDriverCodeByProblemIdAndLanguage(
        @Param("problemId") Long problemId,
        @Param("language") Language language
    );

    @Query(ProblemCodeQuery.USER_FIND_RUN_CODE)
    Optional<RunCodeDto> findRunCodeByProblemIdAndLanguage(
        @Param("problemId") Long problemId,
        @Param("language") Language language
    );

    @Modifying
    @Query(ProblemCodeQuery.UP_DRIVER_CODE)
    void updateDriverCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );

    @Modifying
    @Query(ProblemCodeQuery.UP_USER_CODE)
    void updateUserCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );

    @Modifying
    @Query(ProblemCodeQuery.UP_SOLUTION_CODE)
    void updateSolutionCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );
}
