package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.requests.CreateCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.UpdateCodeRequest;
import com.shahrohit.hashcodex.dtos.responses.AdminCodeItem;

import java.util.List;

public interface AdminProblemCodeService {
    Long create(Integer problemNumber, CreateCodeRequest body);
    List<AdminCodeItem> findCodes(Integer problemNumber);
    void updateDriverCode(Integer problemNumber, Long codeId, UpdateCodeRequest body);
    void updateUserCode(Integer problemNumber, Long codeId, UpdateCodeRequest body);
    void updateSolutionCode(Integer problemNumber, Long codeId, UpdateCodeRequest body);
}
