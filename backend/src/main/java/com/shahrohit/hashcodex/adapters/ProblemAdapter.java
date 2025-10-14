package com.shahrohit.hashcodex.adapters;

import com.shahrohit.hashcodex.dtos.requests.SubmitCodeRequest;
import com.shahrohit.hashcodex.entities.*;
import com.shahrohit.hashcodex.enums.ProblemSubmissionStatus;
import com.shahrohit.hashcodex.dtos.requests.CreateCodeRequest;
import com.shahrohit.hashcodex.dtos.requests.CreateProblemRequest;
import com.shahrohit.hashcodex.dtos.requests.CreateTestcaseRequest;

public class ProblemAdapter {
    public static Problem toEntity(Long problemId) {
        Problem problem = new Problem();
        problem.setId(problemId);
        return problem;
    }

    public static Problem toEntity(Integer problemNumber, CreateProblemRequest body) {
        Problem problem = new Problem();
        problem.setNumber(problemNumber);
        problem.setSlug(body.slug());
        problem.setTitle(body.title());
        problem.setDescription(body.description());
        problem.setDifficulty(body.difficulty());
        problem.setActive(false);
        problem.setParams(body.params());
        problem.setTimeLimit(1.0);
        return problem;
    }

    public static ProblemTopic toEntity(Problem problem, Topic topic) {
        ProblemTopic problemTopic = new ProblemTopic();
        problemTopic.setId(new ProblemTopicId(problem.getId(), topic.getId()));
        problemTopic.setProblem(problem);
        problemTopic.setTopic(topic);
        return problemTopic;
    }

    public static ProblemTestcase toEntity(CreateTestcaseRequest body, Problem problem) {
        ProblemTestcase problemTestcase = new ProblemTestcase();
        problemTestcase.setProblem(problem);
        problemTestcase.setInput(body.input());
        problemTestcase.setOutput(body.output());
        problemTestcase.setSample(body.sample());
        return problemTestcase;
    }

    public static ProblemCode toEntity(CreateCodeRequest body, Problem problem) {
        ProblemCode problemCode = new ProblemCode();
        problemCode.setProblem(problem);
        problemCode.setLanguage(body.language());
        problemCode.setDriverCode(body.driverCode());
        problemCode.setUserCode(body.userCode());
        problemCode.setSolutionCode(body.solutionCode());
        return problemCode;
    }

    public static ProblemSubmission toEntity(Long userId, Long problemId, SubmitCodeRequest body) {
        User user = UserAdapter.toEntity(userId);
        Problem problem = ProblemAdapter.toEntity(problemId);
        ProblemSubmission problemSubmission = new ProblemSubmission();
        problemSubmission.setUser(user);
        problemSubmission.setProblem(problem);
        problemSubmission.setLanguage(body.language());
        problemSubmission.setCode(body.code());
        problemSubmission.setStatus(ProblemSubmissionStatus.PENDING);
        return problemSubmission;
    }
}
