package com.shahrohit.hashcodex.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class providing general-purpose helper methods.
 */
public class Helper {

    private static final String YOUTUBE_PATTERN = "(?:youtube\\.com/(?:.*v=|embed/|v/)|youtu\\.be/)([\\w-]{11})";

    /**
     * Generates a cryptographically secure random token.
     * <p>
     * The token is 256 bits (32 bytes) in size and is Base64 URL-safe encoded
     * without padding, making it suitable for use in URLs, API keys, or tokens.
     * </p>
     *
     * @return a secure, URL-safe, Base64-encoded token string
     */
    public static String generateSecureToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public static String formateSlug(String slug) {
        return slug.replace(' ', '-').toLowerCase();
    }
}
