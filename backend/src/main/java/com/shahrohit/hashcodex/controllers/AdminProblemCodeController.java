package com.shahrohit.hashcodex.controllers;

import com.shahrohit.hashcodex.globals.Response;
import com.shahrohit.hashcodex.globals.SuccessCode;
import com.shahrohit.hashcodex.dtos.requests.CreateCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminCodeItem;
import com.shahrohit.hashcodex.services.AdminProblemCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/admin/problems/codes")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProblemCodeController {
    private final AdminProblemCodeService adminProblemCodeService;

    @GetMapping("/{number}")
    public ResponseEntity<List<AdminCodeItem>> getProblemCode(
        @PathVariable Integer number
    ) {
        List<AdminCodeItem> codes = adminProblemCodeService.findCodes(number);
        return ResponseEntity.ok(codes);
    }

    @PostMapping("/{number}")
    public ResponseEntity<Response<Long>> createProblemCode(
        @PathVariable Integer number,
        @Valid @RequestBody CreateCodeRequest body
    ) {
        Long id = adminProblemCodeService.create(number, body);
        return ResponseEntity.ok(Response.build(SuccessCode.CREATED, id));
    }

    @PatchMapping("/{number}/{codeId}/driver")
    public ResponseEntity<Response<NullType>> updateDriverCode(
        @PathVariable Integer number,
        @PathVariable Long codeId,
        @Valid @RequestBody UpdateCodeRequest body
    ) {
        adminProblemCodeService.updateDriverCode(number, codeId, body);
        return ResponseEntity.ok(Response.build(SuccessCode.CREATED));
    }

    @PatchMapping("/{number}/{codeId}/user")
    public ResponseEntity<Response<NullType>> updateUserCode(
        @PathVariable Integer number,
        @PathVariable Long codeId,
        @Valid @RequestBody UpdateCodeRequest body
    ) {
        adminProblemCodeService.updateUserCode(number, codeId, body);
        return ResponseEntity.ok(Response.build(SuccessCode.CREATED));
    }

    @PatchMapping("/{number}/{codeId}/solution")
    public ResponseEntity<Response<NullType>> updateSolutionCode(
        @PathVariable Integer number,
        @PathVariable Long codeId,
        @Valid @RequestBody UpdateCodeRequest body
    ) {
        adminProblemCodeService.updateSolutionCode(number, codeId, body);
        return ResponseEntity.ok(Response.build(SuccessCode.CREATED));
    }


}
