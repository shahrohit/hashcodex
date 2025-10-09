package com.shahrohit.hashcodex.globals;

import java.util.Map;

/**
 * Standard API error response structure.
 *
 * @param message a human-readable description of the error.
 * @param errors  a map of field-specific errors, used primarily for validation failures; can be {@code null} if not applicable.
 */
public record ErrorResponse(ErrorCode code, String message, Map<String, String> errors) {

    /**
     * Static factory method for building an {@link ErrorResponse} using an {@link ErrorCode}
     * and an optional map of error details.
     *
     * @param code   the error code to include
     * @param errors optional additional error metadata (can be empty or {@code null})
     * @return a new {@link ErrorResponse} instance
     */
    public static ErrorResponse build(ErrorCode code, Map<String, String> errors) {
        return new ErrorResponse(code, code.getMessage(), errors);
    }
}