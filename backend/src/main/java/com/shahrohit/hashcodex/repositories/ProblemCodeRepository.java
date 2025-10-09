package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.entities.ProblemCode;
import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.queries.ProblemQuery;
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
    @Query(ProblemQuery.ADMIN_EXISTS_CODE)
    boolean existsByIdAndProblemNumber(
        @Param("id") Long id,
        @Param("number") Integer problemNumber
    );

    @Query(ProblemQuery.ADMIN_FIND_ALL_CODES)
    List<AdminCodeItem> findCodesByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemQuery.USER_FIND_STARTER_CODE)
    List<Object[]> findStarterCodeByProblemId(@Param("problemId") Long problemId);

    @Query(ProblemQuery.USER_FIND_DRIVER_CODE)
    Optional<String> findDriverCodeByProblemIdAndLanguage(
        @Param("problemId") Long problemId,
        @Param("language") Language language
    );

    @Query(ProblemQuery.USER_FIND_RUN_CODE)
    Optional<RunCodeDto> findRunCodeByProblemIdAndLanguage(
        @Param("problemId") Long problemId,
        @Param("language") Language language
    );


    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_DRIVER_CODE)
    void updateDriverCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_USER_CODE)
    void updateUserCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_SOLUTION_CODE)
    void updateSolutionCodeById(
        @Param("id") Long id,
        @Param("code") String code
    );
}
