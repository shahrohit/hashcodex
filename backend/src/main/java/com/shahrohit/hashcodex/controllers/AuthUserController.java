package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.dtos.requests.*;
import com.shahrohit.hashcodex.modules.sessions.SessionService;
import com.shahrohit.hashcodex.dtos.UserIdAndProfile;
import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.services.AuthUserService;
import com.shahrohit.hashcodex.exceptions.BadRequestException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.utils.Constants;
import com.shahrohit.hashcodex.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthUserService authUserService;
    private final SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<Response<NullType>> register(@Valid @RequestBody CreateUserRequest body) {
        authUserService.register(body);
        return new ResponseEntity<>(Response.build(SuccessCode.CREATED), HttpStatus.CREATED);
    }

    @PatchMapping("/verify-account")
    public ResponseEntity<Response<NullType>> verifyEmail(@Valid @RequestBody VerifyEmailRequest body) {
        authUserService.verifyEmail(body);
        return ResponseEntity.ok(Response.build(SuccessCode.VERIFIED));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<Response<NullType>> forgetPassword(@Valid @RequestBody ForgetPasswordRequest body) {
        authUserService.forgetPassword(body);
        return ResponseEntity.ok(Response.build(SuccessCode.DONE));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Response<NullType>> resetPassword(@Valid @RequestBody ResetPasswordRequest body) {
        if (!Objects.equals(body.password(), body.confirmPassword()))
            throw new BadRequestException(ErrorCode.PASSWORD_NOT_MATCH);
        authUserService.resetResetPassword(body);
        return ResponseEntity.ok(Response.build(SuccessCode.VERIFIED));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserProfile>> login(
        @Valid @RequestBody EmailLoginRequest body,
        HttpServletResponse response
    ) {
        UserIdAndProfile userIdAndProfile = authUserService.login(body);
        if (userIdAndProfile == null) return ResponseEntity.ok(Response.build(SuccessCode.SENT));
        UserProfile user = userIdAndProfile.profile();

        String accessToken = sessionService.createSession(user, userIdAndProfile.id());
        ResponseCookie authCookie = CookieUtils.createAuthCookie(accessToken);
        response.addHeader(Constants.Auth.HEADER_SET_COOKIE, authCookie.toString());

        return ResponseEntity.ok(Response.build(SuccessCode.LOGGED_IN, user));
    }

}
