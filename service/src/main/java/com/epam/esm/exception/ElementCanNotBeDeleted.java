package com.epam.esm.exception;

public class ElementCanNotBeDeleted extends RuntimeException {

    public ElementCanNotBeDeleted(String message) {
        super(message);
    }

    public ElementCanNotBeDeleted(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementCanNotBeDeleted(Throwable cause) {
        super(cause);
    }
}
