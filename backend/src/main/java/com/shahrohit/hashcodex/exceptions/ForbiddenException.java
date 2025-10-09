package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a request is understood but explicitly forbidden by the server.
 * <p>
 * This typically means the authenticated user does not have permission
 * to access the requested resource or perform the requested operation.
 * </p>
 *
 * <p>This exception maps to HTTP status {@code 403 FORBIDDEN}.</p>
 *
 * @see BaseException
 * @see ErrorCode
 */
public class ForbiddenException extends BaseException {

    /**
     * Constructs a new {@code ForbiddenException} with the specified {@link ErrorCode}.
     *
     * @param errorCode the application-specific error code indicating the reason for the forbidden access
     */
    public ForbiddenException(ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode);
    }
}
