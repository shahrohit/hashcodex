package com.shahrohit.hashcodex.globals;

import com.shahrohit.hashcodex.modules.sessions.SessionService;
import com.shahrohit.hashcodex.modules.sessions.SessionUser;
import com.shahrohit.hashcodex.modules.sessions.TokenClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} for loading user details
 * based on an access token (instead of traditional username).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SessionService sessionService;

    /**
     * Loads the user's details using the provided JWT access token.
     *
     * @param accessToken the JWT access token (used in place of username)
     * @return a {@link UserDetails} object constructed from the validated token
     */
    @Override
    public UserDetails loadUserByUsername(String accessToken) {
        TokenClaims tokenClaims = sessionService.getTokenClaims(accessToken);
        SessionUser sessionUser = sessionService.verifyAccessToken(tokenClaims);
        return UserPrincipal.from(sessionUser);
    }
}
