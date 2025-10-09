package com.shahrohit.hashcodex.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahrohit.hashcodex.exceptions.BaseException;
import com.shahrohit.hashcodex.exceptions.ForbiddenException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.globals.ErrorResponse;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.utils.Constants;
import com.shahrohit.hashcodex.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>
 * JWT Authentication Filter that intercepts HTTP requests and validates
 * the JWT access token. If valid, sets the authentication in the security context.
 * </p>
 *
 * <p>This filter is executed once per request.</p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = CookieUtils.getAuthCookie(request.getCookies());

        if (accessToken == null || accessToken.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(accessToken);
            if (userDetails.getAccessToken() != null) {
                ResponseCookie authCookie = CookieUtils.createAuthCookie(userDetails.getAccessToken());
                response.addHeader(Constants.Auth.HEADER_SET_COOKIE, authCookie.toString());
            }

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (ForbiddenException e) {
            setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, e.getErrorCode());
        } catch (Exception e) {
            ErrorCode code = (e instanceof BaseException baseEx) ? baseEx.getErrorCode() : ErrorCode.TOKEN_INVALID;
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, code);
        }
    }

    private void setErrorResponse(HttpServletResponse response, int statusCode, ErrorCode errorCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        ErrorResponse errorResponse = ErrorResponse.build(errorCode, null);
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}