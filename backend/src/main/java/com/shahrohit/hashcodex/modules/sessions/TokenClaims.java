package com.shahrohit.hashcodex.modules.sessions;

import com.shahrohit.hashcodex.enums.Role;

import java.time.Instant;
import java.util.UUID;

/**
 * Encapsulates decoded and validated claims extracted from a JWT token.
 */
public record TokenClaims(UUID subject, Role role, UUID sessionId, Instant expiration, boolean isExpired) {}