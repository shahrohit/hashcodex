package com.shahrohit.hashcodex.dtos.responses;

public record AdminTestcaseItem(
    Long id,
    String input,
    String output,
    Boolean sample
) {}
