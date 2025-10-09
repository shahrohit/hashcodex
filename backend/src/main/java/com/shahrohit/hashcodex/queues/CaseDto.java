package com.shahrohit.hashcodex.queues;

public record CaseDto(
    String input,
    String output,
    String expected,
    String error,
    String status
) {
}
