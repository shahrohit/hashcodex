package com.shahrohit.hashcodex.globals;

import com.shahrohit.hashcodex.modules.sessions.SessionUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * A custom implementation of {@link UserDetails} used for JWT-based authentication.
 * Represents an authenticated user in the Spring Security context.
 */
public class UserPrincipal implements UserDetails {
    @Getter
    private final UUID sessionId;
    @Getter
    private final Long userId;
    @Getter
    private final String accessToken;
    private final boolean enabled;
    private final List<SimpleGrantedAuthority> authorities;

    public static UserPrincipal from(SessionUser u) {
        return new UserPrincipal(u);
    }

    private UserPrincipal(SessionUser u) {
        this.sessionId = u.sessionId();
        this.userId = u.userId();
        this.enabled = u.enabled();
        this.accessToken = u.accessToken();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.role()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}