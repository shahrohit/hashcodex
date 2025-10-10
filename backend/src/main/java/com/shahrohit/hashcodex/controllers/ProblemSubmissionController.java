package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.events.ServerSentEventHub;
import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.globals.UserPrincipal;
import com.shahrohit.hashcodex.dtos.requests.RunCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.SubmitCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.SubmissionItem;
import com.shahrohit.hashcodex.services.ProblemSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/problems/submissions")
@RequiredArgsConstructor
public class ProblemSubmissionController {
    private final ProblemSubmissionService problemSubmission;
    private final ServerSentEventHub serverSentEventHub;

    @PostMapping("/run/{number}")
    public ResponseEntity<Response<String>> run(
        @PathVariable Integer number,
        @Valid @RequestBody RunCodeRequest body,
        @AuthenticationPrincipal UserPrincipal user
    ) {
        String corrId = problemSubmission.runCode(user.getUserId(), number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.DONE, corrId));
    }

    @PostMapping("/submit/{number}")
    public ResponseEntity<Response<String>> submit(
        @PathVariable Integer number,
        @Valid @RequestBody SubmitCodeRequest body,
        @AuthenticationPrincipal UserPrincipal user
    ) {
        String corrId = problemSubmission.submitCode(user.getUserId(), number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.DONE, corrId));
    }

    @GetMapping("/all/{number}")
    public ResponseEntity<List<SubmissionItem>> getAllSubmissions(
        @PathVariable Integer number,
        @AuthenticationPrincipal UserPrincipal user
    ) {
        List<SubmissionItem> submissions = problemSubmission.findAllSubmissions(user.getUserId(), number);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping(value = "/events/{correlationId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@PathVariable String correlationId) {
        SseEmitter em = serverSentEventHub.register(correlationId, TimeUnit.MINUTES.toMillis(30));

        // optional keep-alive + snapshot so client never misses the state
        try {
            em.send(SseEmitter.event().name("ping").data("ok"));
        } catch (Exception ignored) {
        }
        return em;
    }
}
