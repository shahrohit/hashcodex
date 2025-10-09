package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.dtos.responses.ProblemDetail;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import com.shahrohit.hashcodex.dtos.responses.UserProblemItem;
import com.shahrohit.hashcodex.globals.PaginatedResponse;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.services.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserProblemItem>> findAllProblems(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = (principal == null || principal.getUserId() == null) ? null : principal.getUserId();
        Pageable pageable = PageRequest.of(page, size);
        var pageContent = problemService.findProblems(userId, pageable);
        return ResponseEntity.ok(PaginatedResponse.build(pageContent));
    }

    @GetMapping("/topics")
    public ResponseEntity<List<TopicItem>> findAll() {
        List<TopicItem> topics = problemService.findAllTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProblemDetail> findProblemDetail(
        @PathVariable String slug,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long userId = (principal == null || principal.getUserId() == null) ? null : principal.getUserId();
        ProblemDetail problem = problemService.findProblemBySlug(userId, slug);
        return ResponseEntity.ok(problem);
    }
}
