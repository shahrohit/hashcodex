package com.shahrohit.hashcodex.globals;

import lombok.Getter;

@Getter
public enum ErrorCode {
    VALIDATION_ERROR("Invalid data provided"),
    NOT_FOUND("Resource Not found"),

    USER_NOT_FOUND("User Not Found"),
    CREDENTIALS_INVALID("Invalid Credentials"),
    EMAIL_ALREADY_EXISTS("Email already taken"),
    PASSWORD_NOT_MATCH("Password don't match"),
    EMAIL_NOT_FOUND("Email not found"),
    LINK_EXPIRED("Link Expired"),
    SESSION_NOT_FOUND("Session not found"),
    SESSION_EXPIRED("Session Expired"),
    SESSION_INVALID("Invalid Session"),

    // Topics
    TOPIC_ALREADY_EXISTS("Topic already exists"),
    TOPIC_NOT_FOUND("Topic not found"),
    TOPIC_REQUIRED("Topic required"),

    PROBLEM_ALREADY_EXISTS("Problem already exists"),
    PROBLEM_SLUG_ALREADY_EXISTS("Problem slug already exists with the slug"),
    PROBLEM_NOT_FOUND("Problem not found"),
    TESTCASE_NOT_FOUND("Testcase not found"),
    TESTCASE_REQUIRED("Testcase required"),
    CODE_NOT_FOUND("Code not found"),
    CODE_REQUIRED("Code required"),

    TOKEN_INVALID("invalid token"),
    ACCOUNT_DISABLED("your account is disabled"),
    SERVER_ERROR("Server Error");

    private final String message;

    ErrorCode(String message){
        this.message = message;
    }
}
