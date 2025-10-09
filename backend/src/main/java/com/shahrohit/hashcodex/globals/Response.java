package com.shahrohit.hashcodex.globals;

/**
 * Standard API response structure.
 */
public record Response<T>(
    SuccessCode code,
    T data
) {
    public static <T> Response<T> build(SuccessCode code, T data) {
        return new Response<>(code, data);
    }

    public static <T> Response<T> build(SuccessCode code) {
        return new Response<>(code, null);
    }
}
