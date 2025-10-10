package com.shahrohit.hashcodex.modules.sessions;

import com.shahrohit.hashcodex.adapters.SessionAdapter;
import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.entities.Session;
import com.shahrohit.hashcodex.enums.Role;
import com.shahrohit.hashcodex.exceptions.UnauthorizedException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.properties.JwtProperties;
import com.shahrohit.hashcodex.repositories.SessionRepository;
import com.shahrohit.hashcodex.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Service implementation for managing user sessions and validating JWT tokens.
 * <p>Handles session creation, token generation (access and refresh), and validation logic.</p>
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final JwtProperties jwtProperties;
    private final SessionRepository sessionRepository;

    /**
     * Create new sesssion
     *
     * @param user   - {@link UserProfile} of the current user
     * @param userId - user id of the current user
     * @return - JWT access token
     */
    @Override
    public String createSession(UserProfile user, Long userId) {
        Instant createdAt = Instant.now();
        Instant accessTokenExpiresAt = createdAt.plusMillis(jwtProperties.accessTokenExpirationMs());
        Instant refreshTokenExpiresAt = createdAt.plusMillis(jwtProperties.refreshTokenExpirationMs());

        Session session = sessionRepository.save(SessionAdapter.create(userId, createdAt, refreshTokenExpiresAt));
        return generateToken(user.publicId(), user.role(), session.getId(), createdAt, accessTokenExpiresAt);
    }

    /**
     * Revoke the session
     *
     * @param sessionId - the session id
     */
    @Override
    @Transactional
    public void clearSession(UUID sessionId) {
        sessionRepository.deleteSessionById(sessionId);
    }

    /**
     * Verify the access token is valid or not.
     *
     * @param claims {@link TokenClaims} containig extracted token information.
     * @return {@link SessionUser}
     */
    @Override
    @Transactional
    public SessionUser verifyAccessToken(TokenClaims claims) {
        SessionDto session = sessionRepository.findSessionByIdAndPublicId(claims.sessionId(), claims.subject())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.SESSION_NOT_FOUND));

        String accessToken = null;
        if (claims.isExpired()) {
            if (session.expiresAt().isBefore(Instant.now())) {
                throw new UnauthorizedException(ErrorCode.SESSION_EXPIRED);
            }

            Instant createdAt = Instant.now();
            Instant accessTokenExpiresAt = createdAt.plusMillis(jwtProperties.accessTokenExpirationMs());
            Instant refreshTokenExpiresAt = createdAt.plusMillis(jwtProperties.refreshTokenExpirationMs());
            sessionRepository.updateSessionExpiryBYId(session.sessionId(), refreshTokenExpiresAt);
            accessToken = generateToken(claims.subject(), session.role(), session.sessionId(), createdAt, accessTokenExpiresAt);
        }

        return new SessionUser(session.sessionId(), session.userId(), accessToken, session.enabled(), session.role());
    }

    /**
     * Extract the token claims from the JWT token.
     *
     * @return {@link TokenClaims}
     */
    @Override
    public TokenClaims getTokenClaims(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            UUID subject = extractSubject(claims);
            Role role = extractRole(claims);
            UUID sessionId = extractSessionId(claims);
            Instant expiration = extractExpiration(claims);
            return new TokenClaims(subject, role, sessionId, expiration, false);
        } catch (ExpiredJwtException ex) {
            Claims claims = ex.getClaims();
            UUID subject = extractSubject(claims);
            Role role = extractRole(claims);
            UUID sessionId = extractSessionId(claims);
            return new TokenClaims(subject, role, sessionId, null, true);

        }
    }

    /**
     * Retrieves the signing key used for JWT token signing and validation.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate JWT Access Token
     */
    private String generateToken(UUID subject, Role role, UUID sessionId, Instant createdAt, Instant expiresAt) {
        return Jwts.builder()
            .subject(subject.toString())
            .issuedAt(Date.from(createdAt))
            .expiration(Date.from(expiresAt))
            .claim(Constants.Auth.JWT_CLAIM_ROLE, role.name())
            .claim(Constants.Auth.JWT_CLAIM_SESSION, sessionId.toString())
            .signWith(getSigningKey()).compact();
    }

    /**
     * Parses JWT token and extracts the claims payload.
     *
     * @param token the JWT string
     * @return the extracted {@link Claims}
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Extracts the subject (user's public ID) from token claims.
     *
     * @param claims the JWT claims
     * @return user's public ID as {@link UUID}
     */
    private UUID extractSubject(Claims claims) {
        return UUID.fromString(claims.getSubject());
    }

    /**
     * Extracts the role from token claims.
     *
     * @param claims the JWT claims
     * @return the {@link Role}
     */
    private Role extractRole(Claims claims) {
        String role = claims.get(Constants.Auth.JWT_CLAIM_ROLE, String.class);
        return Role.valueOf(role);
    }

    /**
     * Extracts the session ID from token claims.
     *
     * @param claims the JWT claims
     * @return session ID as {@link UUID}
     */
    private UUID extractSessionId(Claims claims) {
        String sessionId = claims.get(Constants.Auth.JWT_CLAIM_SESSION, String.class);
        return UUID.fromString(sessionId);
    }

    /**
     * Extracts the token expiration∂ from token claims.
     *
     * @param claims the JWT claims
     * @return token expiration
     */
    private Instant extractExpiration(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.toInstant();
    }
}
