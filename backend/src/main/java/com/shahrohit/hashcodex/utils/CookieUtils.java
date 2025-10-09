package com.shahrohit.hashcodex.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;

import java.time.Duration;
import java.util.Arrays;

/**
 * Util class for managing authentication cookies.
 * <p>
 * This component handles creating, retrieving, and clearing cookies related to
 * authentication (specifically the refresh token).
 * <p>
 */
public class CookieUtils {

    private CookieUtils() {}

    /**
     * Retrieves the value of a cookie by name from a given array of {@link Cookie} objects.
     *
     * @param cookies    the array of cookies to search
     * @param cookieName the name of the cookie to retrieve
     * @return the cookie value if found, or {@code null} otherwise
     */
    public static String getCookie(Cookie[] cookies, String cookieName) {
        if (cookies == null || cookies.length == 0) return null;
        return Arrays.stream(cookies)
            .filter(cookie -> cookie != null && cookie.getName().equals(cookieName))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
    }

    /**
     * Retrieves the value of the authentication refresh token cookie from a given array of cookies.
     *
     * @param cookies the array of cookies to search
     * @return the refresh token value if present, or {@code null} otherwise
     */
    public static String getAuthCookie(Cookie[] cookies) {
        return getCookie(cookies, Constants.Auth.COOKIE_REFRESH_TOKEN);
    }

    /**
     * Creates a new HTTP-only refresh token cookie for authentication.
     * <p>The cookie is secure if the active profile is {@code PROD}, and is set to expire in 7 days.</p>
     *
     * @param refreshToken the refresh token to be stored in the cookie
     * @return a {@link ResponseCookie} configured with authentication properties
     */
    public static ResponseCookie createAuthCookie(String refreshToken) {
        return ResponseCookie.from(Constants.Auth.COOKIE_REFRESH_TOKEN, refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofDays(7))
            .sameSite("None")
            .build();
    }

    /**
     * Creates a cookie to effectively clear the refresh token from the browser.
     *
     * @return a {@link ResponseCookie} configured to clear the authentication cookie
     */
    public static ResponseCookie clearAuthCookie() {
        return ResponseCookie.from(Constants.Auth.COOKIE_REFRESH_TOKEN, "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofDays(7))
            .sameSite("None")
            .build();
    }
}
