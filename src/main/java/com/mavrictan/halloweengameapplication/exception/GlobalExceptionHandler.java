package com.mavrictan.halloweengameapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    public final ResponseEntity<ApiError> handleException(Exception ex) {

        if (ex instanceof DuplicatedEntityException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            DuplicatedEntityException unfe = (DuplicatedEntityException) ex;

            return handleUserNotFoundException(unfe, status);
        }
        return new ResponseEntity<>(new ApiError("An error has occured"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Customize the response for UserNotFoundException.
     */
    protected ResponseEntity<ApiError> handleUserNotFoundException(DuplicatedEntityException ex, HttpStatus status) {
        return handleExceptionInternal(ex, new ApiError(ex.getMessage()), status);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpStatus status) {

        return new ResponseEntity<>(body, status);
    }

    @AllArgsConstructor
    @Data
    class ApiError {
        private String error;
    }
}

