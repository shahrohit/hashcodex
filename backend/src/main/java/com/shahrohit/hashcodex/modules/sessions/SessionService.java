package com.shahrohit.hashcodex.modules.sessions;

import com.shahrohit.hashcodex.dtos.UserProfile;

import java.util.UUID;

public interface SessionService {
    String createSession(UserProfile user, Long userId);
    TokenClaims getTokenClaims(String token);
    SessionUser verifyAccessToken(TokenClaims claims);
    void clearSession(UUID sessionId);
}
