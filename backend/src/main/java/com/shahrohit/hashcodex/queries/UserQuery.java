package com.shahrohit.hashcodex.queries;

public interface UserQuery {

    // language=JPQL
    String FIND_PID_AND_NAME_BY_EMAIL = """
            SELECT new com.shahrohit.hashcodex.dtos.PublicIdAndName(u.publicId, u.name)
            FROM User u WHERE u.email = :email
            """;

    // language=JPQL
    String FIND_USER_PROFILE = """
        SELECT new com.shahrohit.hashcodex.dtos.UserProfile(u.publicId, u.name, u.email, u.role, u.emailVerified, u.enabled)
        FROM User u WHERE u.id = :id
        """;

    // language=JPQL
    String UPDATE_EMAIL_VERIFIED_BY_PID = "UPDATE User u SET u.emailVerified = TRUE, u.updatedAt = :updatedAt where u.publicId = :publicId";

    // language=JPQL
    String UPDATE_PASSWORD_BY_PID = "UPDATE User u SET u.hashedPassword = :hashedPassword, u.updatedAt = :updatedAt where u.publicId = :publicId";

}
