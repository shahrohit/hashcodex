package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemItem;
import com.shahrohit.hashcodex.dtos.responses.UserProblemItem;
import com.shahrohit.hashcodex.entities.Problem;
import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import com.shahrohit.hashcodex.queries.ProblemQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsBySlug(@Param("slug") String slug);

    boolean existsByNumber(@Param("number") Integer number);

    @Query(ProblemQuery.FIND_ID_BY_NUM)
    Optional<Long> findIdByNumber(@Param("number") Integer number);

    @Query(ProblemQuery.ADMIN_PAGE_ALL)
    Page<AdminProblemItem> findAllProblems(Pageable pageable);

    @Query(ProblemQuery.ADMIN_PAGE_ALL_BY_QUERY)
    Page<AdminProblemItem> findAllProblemsByQuery(
        @Param("isNumeric") boolean isNumeric,
        @Param("number") int number,
        @Param("title") String title,
        Pageable pageable
    );

    @Query(ProblemQuery.ADMIN_FIND_BY_SLUG)
    Optional<AdminProblemDetail> findProblemDetailBySlug(@Param("slug") String slug);

    @Query(ProblemQuery.USER_PAGE_PROBLEMS)
    Page<UserProblemItem> findUserProblems(
        @Param("userId") Long userId,
        Pageable pageable
    );

    @Query(ProblemQuery.PUBLIC_PAGE_PROBLEMS)
    Page<UserProblemItem> findPublicProblems(Pageable pageable);

    @Query(ProblemQuery.FIND_ACTIVE_PROBLEM_BY_SLUG)
    Optional<Problem> findActiveProblemBySlug(@Param("slug") String slug);

    @Query(ProblemQuery.FIND_ID_BY_ACTIVE_NUM)
    Optional<Long> findIdByActiveNumber(@Param("number") Integer number);

    @Modifying
    @Query(ProblemQuery.UP_BASIC_BY_NUM)
    int updateBasicByNumber(
        @Param("number") Integer number,
        @Param("title") String title,
        @Param("difficulty") ProblemDifficulty difficulty,
        @Param("params") String params,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.UP_DESC_BY_NUM)
    void updateDescriptionByNumber(
        @Param("number") Integer number,
        @Param("description") String description,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.UP_SLUG_BY_NUM)
    int updateSlugByNumber(
        @Param("number") Integer number,
        @Param("slug") String slug,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.UP_ACTIVE_BY_ID)
    void updateActiveById(
        @Param("id") Long id,
        @Param("active") Boolean active,
        @Param("updatedAt") Instant updatedAt
    );
}
