package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the client sends a malformed or invalid request.
 * <p>
 * This exception is typically used to indicate issues like:
 * missing required fields, invalid parameter formats, or failed validations.
 * </p>
 *
 * <p>This exception maps to HTTP status {@code 400 BAD_REQUEST}.</p>
 *
 * @see BaseException
 * @see ErrorCode
 */
public class BadRequestException extends BaseException {

    /**
     * Constructs a new {@code BadRequestException} with the specified {@link ErrorCode}.
     *
     * @param errorCode the application-specific error code indicating the reason for the bad request
     */
    public BadRequestException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
