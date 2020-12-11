package com.github.jarnaud.republican.exception;

/**
 * A RuntimeException thrown while manipulating the Republican calendar with invalid parameters.
 */
public class RepublicanCalendarException extends RuntimeException {

    public RepublicanCalendarException() {
    }

    public RepublicanCalendarException(String message) {
        super(message);
    }
}
