package com.shahrohit.hashcodex.exceptions;

import com.shahrohit.hashcodex.globals.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Abstract base class for all custom runtime exceptions in the application.
 * <p>
 * Provides consistent structure for handling HTTP status codes and error codes.
 * Used as a superclass for domain-specific exceptions (e.g., {@link UnauthorizedException}, {@link BadRequestException}).
 * </p>
 *
 * <p>The message of this exception is automatically set to the {@code messageKey} from the {@link ErrorCode}.</p>
 *
 * @see ErrorCode
 * @see HttpStatus
 */
@Getter
public abstract class BaseException extends RuntimeException {
    /**
     * The HTTP status to return for this exception.
     */
    private final HttpStatus status;

    /**
     * The application-specific error code representing the exception.
     */
    private final ErrorCode errorCode;

    /**
     * Constructs a new BaseException.
     *
     * @param status    the HTTP status to associate with this exception
     * @param errorCode the application-defined error code containing a message key.
     */
    protected BaseException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }
}
