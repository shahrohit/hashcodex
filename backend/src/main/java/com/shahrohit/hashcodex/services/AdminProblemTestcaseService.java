package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.requests.CreateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateTestcaseRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminTestcaseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProblemTestcaseService {
    Long create(Integer problemNumber, CreateTestcaseRequest body);
    Page<AdminTestcaseItem> findTestcases(Integer problemNumber, Pageable pageable);
    void updateInput(Integer problemNumber, Long testcaseId, UpdateTestcaseRequest body);
    void updateOutput(Integer problemNumber, Long testcaseId, UpdateTestcaseRequest body);
    void updateSample(Integer problemNumber, Long testcaseId, Boolean sample);
    void deleteTestcase(Integer problemNumber, Long testcaseId);
}
