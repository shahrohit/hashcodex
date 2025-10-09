package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when authentication is required and has either failed or not been provided.
 * <p>
 * This exception is typically used to indicate that
 * the user is not authenticated or the provided credentials are invalid.
 * </p>
 *
 * <p>This exception maps to HTTP status {@code 401 UNAUTHORIZED}.</p>
 *
 * @see BaseException
 * @see ErrorCode
 */
public class UnauthorizedException extends BaseException {

    /**
     * Constructs a new {@code UnauthorizedException} with the specified {@link ErrorCode}.
     *
     * @param errorCode the application-specific error code indicating the reason for the unauthorized access
     */
    public UnauthorizedException(ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }
}
