package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource cannot be found.
 * <p>
 * This is typically used in situations where a database query returns no result
 * or an entity with the specified identifier does not exist.
 * </p>
 *
 * <p>This exception maps to HTTP status {@code 404 NOT_FOUND}.</p>
 *
 * @see BaseException
 * @see ErrorCode
 */
public class NotFoundException extends BaseException {
    public NotFoundException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
