package com.shahrohit.hashcodex.queries;

public interface SessionQuery {
    // language=JPQL
    String FIND_SESSION_USER = """
        SELECT new com.shahrohit.hashcodex.modules.sessions.SessionUser(
            s.id,
            u.id,
            s.expiresAt,
            new com.shahrohit.hashcodex.dtos.UserProfile(u.publicId, u.name, u.email, u.role, u.emailVerified, u.enabled)
        )
        FROM Session s
        JOIN User u
        """;

    // language=JPQL
    String FIND_SESSION_BY_ID_AND_PID = """
        SELECT new com.shahrohit.hashcodex.modules.sessions.SessionDto(s.id, u.id, s.expiresAt, u.enabled, u.role)
        FROM Session s
        JOIN User u on s.user.id = u.id
        WHERE s.id = :id AND u.publicId = :publicId
        """;

    // language=JPQL
    String UP_SESSION_EXPIRY_BY_ID = "UPDATE Session s SET s.expiresAt = :expiresAt WHERE s.id = :id";

    // language=JPQL
    String DEL_SESSION_BY_ID = "DELETE FROM Session s WHERE s.id = :id";

}
