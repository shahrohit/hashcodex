package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.globals.PaginatedResponse;
import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.dtos.requests.CreateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem;
import com.shahrohit.hashcodex.services.AdminProblemTestcaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;

@RestController
@RequestMapping("/api/admin/problems/testcases")
@RequiredArgsConstructor
public class AdminProblemTestcaseController {
    private final AdminProblemTestcaseService adminProblemTestcaseService;

    @PostMapping("/{number}")
    public ResponseEntity<Response<Long>> createTestcase(
        @PathVariable Integer number,
        @Valid @RequestBody CreateTestcaseRequest body
    ) {
        Long id = adminProblemTestcaseService.create(number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.CREATED, id));
    }

    @GetMapping("/{number}")
    public ResponseEntity<PaginatedResponse<AdminTestcaseItem>> findTestcase(
        @PathVariable Integer number,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        var pageContents = adminProblemTestcaseService.findTestcases(number, pageable);
        return ResponseEntity.ok(PaginatedResponse.build(pageContents));
    }

    @PatchMapping("/{number}/{testcaseId}/sample")
    public ResponseEntity<Response<NullType>> updateSample(
        @PathVariable Integer number,
        @PathVariable Long testcaseId,
        @RequestParam Boolean sample
    ) {
        adminProblemTestcaseService.updateSample(number, testcaseId, sample);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @PatchMapping("/{number}/{testcaseId}/input")
    public ResponseEntity<Response<NullType>> updateInputTestcase(
        @PathVariable Integer number,
        @PathVariable Long testcaseId,
        @Valid @RequestBody UpdateTestcaseRequest body
    ) {
        adminProblemTestcaseService.updateInput(number, testcaseId, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @PatchMapping("/{number}/{testcaseId}/output")
    public ResponseEntity<Response<NullType>> updateOutputTestcase(
        @PathVariable Integer number,
        @PathVariable Long testcaseId,
        @Valid @RequestBody UpdateTestcaseRequest body
    ) {
        adminProblemTestcaseService.updateOutput(number, testcaseId, body);
        return ResponseEntity.ok(Response.build(SuccessCode.UPDATED));
    }

    @DeleteMapping("/{number}/{testcaseId}")
    public ResponseEntity<Response<NullType>> deleteOutputTestcase(
        @PathVariable Integer number,
        @PathVariable Long testcaseId
    ) {
        adminProblemTestcaseService.deleteTestcase(number, testcaseId);
        return ResponseEntity.ok(Response.build(SuccessCode.DELETED));
    }
}
