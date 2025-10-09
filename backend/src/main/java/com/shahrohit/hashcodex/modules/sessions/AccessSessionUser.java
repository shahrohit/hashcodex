package com.shahrohit.hashcodex.modules.sessions;

import com.shahrohit.hashcodex.enums.Role;

import java.time.Instant;
import java.util.UUID;

public record AccessSessionUser(
    UUID sessionId,
    Long userId,
    Instant expiresAt,
    Boolean enabled,
    Role role
) {}
