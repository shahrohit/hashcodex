package com.shahrohit.hashcodex.modules.sessions;

import com.shahrohit.hashcodex.enums.Role;
import java.util.UUID;

/**
 * Represents a lightweight session view containing essential session-related data.
 */
public record SessionUser(
    UUID sessionId,
    Long userId,
    String accessToken,
    boolean enabled,
    Role role
) {}
