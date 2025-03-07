package com.github.jaguzmanb1.quasar.exception;

/**
 * Exception thrown when the intersection calculation is invalid.
 * This typically occurs when trilateration fails due to incorrect or insufficient data.
 */
public class InvalidIntersectionException extends RuntimeException {

    /**
     * Constructs a new InvalidIntersectionException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public InvalidIntersectionException(String message) {
        super(message);
    }
}
