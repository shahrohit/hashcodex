package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me(@AuthenticationPrincipal UserPrincipal principal) {
        UserProfile user = userService.me(principal.getUserId());
        return ResponseEntity.ok(user);
    }
}
