package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.dtos.PublicIdAndName;
import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.entities.User;
import com.shahrohit.hashcodex.queries.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(UserQuery.FIND_PID_AND_NAME_BY_EMAIL)
    Optional<PublicIdAndName> findPublicIdAndNameByEmail(@Param("email") String email);

    @Query(UserQuery.FIND_USER_PROFILE_BY_ID)
    Optional<UserProfile> findUserProfileById(@Param("id") Long userId);

    @Modifying
    @Query(UserQuery.UP_EMAIL_VERIFY_BY_PID)
    int updateEmailVerifiedByPublicId(
        @Param("publicId") UUID publicId,
        @Param("updatedAt") Instant updatedAt
    );

    @Modifying
    @Query(UserQuery.UP_PASS_BY_PID)
    int updatePasswordByPublicId(
        @Param("publicId") UUID publicId,
        @Param("hashedPassword") String hashedPassword,
        @Param("updatedAt") Instant updatedAt
    );
}
