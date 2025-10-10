package com.shahrohit.hashcodex.globals;

import com.shahrohit.hashcodex.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * <p>
 * Catches and processes exceptions thrown by any controller across the application,
 * providing a consistent error response structure.
 * </p>
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String errorKey = "error";

    /**
     * Handles validation errors from {@code @Valid} annotated parameters.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(ErrorResponse.build(ErrorCode.VALIDATION_ERROR, errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles errors where a request parameter type does not match the expected type.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";
        String error = String.format("Type mismatch: Parameter '%s' must be of type '%s' but got value '%s'", name, type, value);
        Map<String, String> errors = Map.of(errorKey, error);

        return new ResponseEntity<>(ErrorResponse.build(ErrorCode.VALIDATION_ERROR, errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom application exceptions derived from {@link BaseException}.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ErrorResponse.build(ex.getErrorCode(), null), ex.getStatus());
    }

    /**
     * Handles error when the requirest resource not found.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.build(ErrorCode.NOT_FOUND, Map.of(errorKey, ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles error when the requirest data can be parsed.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ErrorResponse.build(ErrorCode.VALIDATION_ERROR, null), HttpStatus.NOT_FOUND);
    }

    /**
     * Catches any unhandled exceptions and returns a generic server error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        // TODO: Fix the logs for the production
        log.error("({})[{}]", ex.getClass().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.build(ErrorCode.SERVER_ERROR, Map.of(errorKey, ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
