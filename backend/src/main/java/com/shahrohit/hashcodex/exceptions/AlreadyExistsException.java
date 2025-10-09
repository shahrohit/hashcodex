package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a resource already exists and cannot be created again.
 * <p>
 * Typically used in scenarios like attempting to register a user with an existing email,
 * creating a duplicate entry, etc.
 * </p>
 *
 * <p>This exception maps to HTTP status {@code 409 CONFLICT}.</p>
 *
 * @see BaseException
 * @see ErrorCode
 */
public class AlreadyExistsException extends BaseException {

    /**
     * Constructs a new {@code AlreadyExistsException} with the specified {@link ErrorCode}.
     *
     * @param errorCode the application-specific error code representing the conflict reason
     */
    public AlreadyExistsException(ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, errorCode);
    }
}
