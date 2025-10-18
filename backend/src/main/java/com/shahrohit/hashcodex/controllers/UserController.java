package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.modules.sessions.SessionService;
import com.shahrohit.hashcodex.services.UserService;
import com.shahrohit.hashcodex.utils.Constants;
import com.shahrohit.hashcodex.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.type.NullType;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me(@AuthenticationPrincipal UserPrincipal principal) {
        UserProfile user = userService.me(principal.getUserId());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<NullType>> logout(@AuthenticationPrincipal UserPrincipal user, HttpServletResponse response) {
        sessionService.clearSession(user.getSessionId());
        ResponseCookie authCookie = CookieUtils.clearAuthCookie();
        response.addHeader(Constants.Auth.HEADER_SET_COOKIE, authCookie.toString());
        return ResponseEntity.ok(Response.build(SuccessCode.LOGGED_OUT));
    }
}
