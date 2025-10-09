package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.modules.sessions.AccessSessionUser;
import com.shahrohit.hashcodex.entities.Session;
import com.shahrohit.hashcodex.queries.SessionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Query(SessionQuery.FIND_ACCESS_SESSION_USER)
    Optional<AccessSessionUser> findAccessSessionUserById(
        @Param("id") UUID id,
        @Param("publicId") UUID publicId
    );

    @Modifying
    @Query(SessionQuery.UPDATE_SESSION)
    void updateSession(
        @Param("id") UUID sessionId,
        @Param("expiresAt") Instant expiresAt
    );

    @Modifying
    @Transactional
    @Query(SessionQuery.DELETE_SESSION)
    void deleteSessionById(@Param(("id")) UUID id);
}
