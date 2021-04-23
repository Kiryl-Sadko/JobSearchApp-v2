package com.epam.esm.exception;

public class ParseToCalendarException extends RuntimeException {

    public ParseToCalendarException(String message) {
        super(message);
    }

    public ParseToCalendarException(String message, Throwable cause) {
        super(message, cause);
    }
}
