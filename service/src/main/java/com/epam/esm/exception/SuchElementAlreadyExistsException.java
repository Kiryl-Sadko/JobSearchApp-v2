package com.epam.esm.exception;

public class SuchElementAlreadyExistsException extends RuntimeException {

    public SuchElementAlreadyExistsException(String message) {
        super(message);
    }

    public SuchElementAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuchElementAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
