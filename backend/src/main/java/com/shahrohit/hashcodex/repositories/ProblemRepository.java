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
import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsBySlug(String slug);

    boolean existsByNumber(Integer number);

    @Query(ProblemQuery.ADMIN_EXISTS_TESTCASES_BY_PROBLEM_ID)
    boolean existsProblemTestcases(@Param("problemId") Long problemId);

    @Query(ProblemQuery.ADMIN_EXISTS_CODES_BY_PROBLEM_ID)
    boolean existsProblemCodes(@Param("problemId") Long problemId, @Param("langCount") long langCount);

    @Query(ProblemQuery.ADMIN_FIND_ID_BY_NUMBER)
    Optional<Long> findIdByNumber(Integer number);

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
    Page<UserProblemItem> findUserProblems(Long userId, Pageable pageable);

    @Query(ProblemQuery.PUBLIC_PAGE_PROBLEMS)
    Page<UserProblemItem> findPublicProblems(Pageable pageable);

    @Query(ProblemQuery.USER_FIND_ACTIVE_PROBLEM_BY_SLUG)
    Optional<Problem> findActiveProblemBySlug(@Param("slug") String slug);

    @Query(ProblemQuery.USER_FIND_ID_BY_NUMBER)
    Optional<Long> findIdByActiveNumber(@Param("number") Integer number);

    @Query(ProblemQuery.USER_FIND_TOPIC_NAMES_BY_ID)
    List<String> findTopicNamesByProblemId(@Param("problemId") Long problemId);


    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_BASIC_BY_NUMBER)
    int updateBasicByNumber(
        @Param("number") Integer number,
        @Param("title") String title,
        @Param("difficulty") ProblemDifficulty difficulty,
        @Param("params") String params,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_DESCRIPTION_BY_NUMBER)
    void updateDescriptionByNumber(
        @Param("number") Integer number,
        @Param("description") String description,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_SLUG_BY_NUMBER)
    int updateSlugByNumber(
        @Param("number") Integer number,
        @Param("slug") String slug,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(ProblemQuery.ADMIN_UPDATE_ACTIVE)
    void updateActiveById(
        @Param("id") Long id,
        @Param("active") Boolean active,
        @Param("updatedAt") Instant updatedAt
    );
}
