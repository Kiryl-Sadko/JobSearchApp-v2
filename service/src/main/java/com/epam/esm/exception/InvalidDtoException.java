package com.epam.esm.exception;

public class InvalidDtoException extends RuntimeException {

    public InvalidDtoException() {
        super();
    }

    public InvalidDtoException(String message) {
        super(message);
    }

    public InvalidDtoException(String message, Throwable cause) {
        super(message, cause);
    }
}
