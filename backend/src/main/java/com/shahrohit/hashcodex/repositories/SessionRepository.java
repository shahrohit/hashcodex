package com.shahrohit.hashcodex.repositories;

import com.shahrohit.hashcodex.modules.sessions.SessionDto;
import com.shahrohit.hashcodex.entities.Session;
import com.shahrohit.hashcodex.queries.SessionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Query(SessionQuery.FIND_SESSION_BY_ID_AND_PID)
    Optional<SessionDto> findSessionByIdAndPublicId(
        @Param("id") UUID id,
        @Param("publicId") UUID publicId
    );

    @Modifying
    @Query(SessionQuery.UP_SESSION_EXPIRY_BY_ID)
    void updateSessionExpiryBYId(
        @Param("id") UUID sessionId,
        @Param("expiresAt") Instant expiresAt
    );

    @Modifying
    @Query(SessionQuery.DEL_SESSION_BY_ID)
    void deleteSessionById(@Param(("id")) UUID id);
}
