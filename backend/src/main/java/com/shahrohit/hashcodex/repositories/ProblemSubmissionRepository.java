package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.entities.ProblemSubmission;
import com.shahrohit.hashcodex.enums.ProblemSubmissionStatus;
import com.shahrohit.hashcodex.queries.ProblemQuery;
import com.shahrohit.hashcodex.dtos.responses.SubmissionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProblemSubmissionRepository extends JpaRepository<ProblemSubmission, Long> {
    @Query(ProblemQuery.USER_EXIST_PROBLEM_SOLVED)
    boolean existsProblemSolved(@Param("userId") Long userId, @Param("problemId") Long problemId);

    @Query(ProblemQuery.USER_EXIST_PROBLEM_ATTEMPTED)
    boolean existProblemAttempted(@Param("userId") Long userId, @Param("problemId") Long problemId);

    @Query(ProblemQuery.USER_FIND_SUBMISSIONS)
    List<SubmissionItem> findProblemSubmissionsByUser(
        @Param("userId") Long userId,
        @Param("number") Integer number
    );

    @Transactional
    @Modifying
    @Query(ProblemQuery.USER_UPDATE_SUBMISSION_STATUS)
    void updateSubmissionStatus(@Param("id") Long id, @Param("status") ProblemSubmissionStatus status);
}
