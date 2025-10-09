package com.shahrohit.hashcodex.modules.sessions;

/**
 * Encapsulates generate Access and Refresh JWT token.
 */
public record TokenDto(String accessToken, String refreshToken) {}
