package com.shahrohit.hashcodex.queries;

public interface SessionQuery {
    // language=JPQL
    String FIND_SESSION_USER = """
        SELECT new com.shahrohit.hashcodex.modules.sessions.SessionUser(
            s.id,
            u.id,
            s.expiresAt,
            new com.shahrohit.hashcodex.dtos.UserProfile(u.publicId, u.name, u.email, u.profilePicture, u.role, u.emailVerified, u.enabled)
        )
        FROM Session s
        JOIN User u
        """;

    // language=JPQL
    String FIND_ACCESS_SESSION_USER = """
        SELECT new com.shahrohit.hashcodex.modules.sessions.AccessSessionUser(s.id, u.id, s.expiresAt, u.enabled, u.role)
        FROM Session s
        JOIN User u on s.user.id = u.id
        WHERE s.id = :id AND u.publicId = :publicId
        """;

    // language=JPQL
    String UPDATE_SESSION = "UPDATE Session s SET s.expiresAt = :expiresAt WHERE s.id = :id";

    // language=JPQL

    // language=JPQL
    String DELETE_SESSION = "DELETE FROM Session s WHERE s.id = :id";

}
