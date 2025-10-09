package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.dtos.requests.CreateTopicRequest;
import com.shahrohit.hashcodex.services.AdminTopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;

@RestController
@RequestMapping("/api/admin/topics")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminTopicController {
    private final AdminTopicService adminTopicService;

    @PostMapping
    public ResponseEntity<Response<NullType>> create(@Valid @RequestBody CreateTopicRequest body) {
        adminTopicService.create(body);
        return new ResponseEntity<>(Response.build(SuccessCode.CREATED), HttpStatus.CREATED);
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<Response<NullType>> update(@PathVariable String slug, @Valid @RequestBody CreateTopicRequest body) {
        adminTopicService.updateBySlug(slug, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Response<NullType>> delete(@PathVariable String slug) {
        adminTopicService.deleteBySlug(slug);
        return ResponseEntity.ok(Response.build(SuccessCode.DELETED));
    }

}
