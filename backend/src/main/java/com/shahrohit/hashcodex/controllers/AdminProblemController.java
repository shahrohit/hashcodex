package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.globals.PaginatedResponse;
import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.dtos.requests.CreateProblemRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemBasicRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateProblemDescriptionRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateSlugRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemDetail;
import com.shahrohit.hashcodex.dtos.responses.AdminProblemItem;
import com.shahrohit.hashcodex.services.AdminProblemService;
import com.shahrohit.hashcodex.dtos.responses.TopicItem;
import com.shahrohit.hashcodex.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/admin/problems")
@RequiredArgsConstructor
public class AdminProblemController {
    private final AdminProblemService adminProblemService;

    @PostMapping
    public ResponseEntity<Response<NullType>> create(@Valid @RequestBody CreateProblemRequest body) {
        adminProblemService.create(body);
        return new ResponseEntity<>(Response.build(SuccessCode.CREATED), HttpStatus.CREATED);
    }

    @PostMapping("/{number}/topics/{topicSlug}")
    public ResponseEntity<Response<NullType>> addProblemTopic(
        @PathVariable("number") Integer problemNumber,
        @PathVariable String topicSlug
    ) {
        adminProblemService.addTopic(problemNumber, topicSlug);
        return ResponseEntity.ok(Response.build(SuccessCode.ADDED));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<AdminProblemItem>> getProblems(
        @RequestParam(required = false) String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, Constants.FIELD.ID));
        var problemPage = adminProblemService.findAll(query, pageable);
        return ResponseEntity.ok(PaginatedResponse.build(problemPage));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<AdminProblemDetail> getProblem(@PathVariable String slug) {
        if (slug == null || slug.isEmpty()) throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        AdminProblemDetail problem = adminProblemService.findProblemDetail(slug);
        return ResponseEntity.ok(problem);
    }

    @GetMapping("/{number}/topics")
    public ResponseEntity<List<TopicItem>> getProblem(@PathVariable Integer number) {
        List<TopicItem> topics = adminProblemService.findProblemTopics(number);
        return ResponseEntity.ok(topics);
    }

    @PatchMapping("/{number}/slug")
    public ResponseEntity<Response<String>> updateSlugStatus(
        @PathVariable Integer number,
        @Valid @RequestBody UpdateSlugRequest body
    ) {
        String updateSlug = adminProblemService.updateSlug(number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED, updateSlug));
    }

    @PatchMapping("/{number}/basic")
    public ResponseEntity<Response<NullType>> updateBasicDetail(
        @PathVariable Integer number,
        @Valid @RequestBody UpdateProblemBasicRequest body
    ) {
        adminProblemService.updateBasicDetail(number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @PatchMapping("/{number}/description")
    public ResponseEntity<Response<NullType>> updateDescription(
        @PathVariable Integer number,
        @Valid @RequestBody UpdateProblemDescriptionRequest body
    ) {
        adminProblemService.updateDescription(number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }


    @PatchMapping("/{number}/active")
    public ResponseEntity<Response<NullType>> updateActiveStatus(
        @PathVariable Integer number,
        @RequestParam Boolean active
    ) {
        adminProblemService.updateActive(number, active);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @DeleteMapping("/{number}/topics/{topicSlug}")
    public ResponseEntity<Response<NullType>> deleteProblemTopic(
        @PathVariable("number") Integer problemNumber,
        @PathVariable String topicSlug
    ) {
        adminProblemService.removeTopic(problemNumber, topicSlug);
        return ResponseEntity.ok(Response.build(SuccessCode.ADDED));
    }

}
