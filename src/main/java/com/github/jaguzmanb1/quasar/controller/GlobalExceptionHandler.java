package com.github.jaguzmanb1.quasar.controller;

import com.github.jaguzmanb1.quasar.exception.InvalidIntersectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage application-wide exceptions.
 * This class provides centralized exception handling for different types of errors.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type InvalidIntersectionException.
     * Returns a NOT_FOUND (404) HTTP status with the corresponding error message.
     *
     * @param ex the InvalidIntersectionException thrown by the application
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(InvalidIntersectionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidIntersection(InvalidIntersectionException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles exceptions of type MethodArgumentNotValidException.
     * Returns a BAD_REQUEST (400) HTTP status with the validation error message.
     *
     * @param ex the MethodArgumentNotValidException thrown when request validation fails
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequest(MethodArgumentNotValidException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles generic exceptions that are not explicitly caught by other handlers.
     * Returns an INTERNAL_SERVER_ERROR (500) HTTP status with the corresponding error message.
     *
     * @param ex the general Exception thrown by the application
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Utility method to construct error response entities.
     *
     * @param status the HTTP status to be returned
     * @param message the error message to be included in the response
     * @return a ResponseEntity containing the error message and HTTP status
     */
    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
