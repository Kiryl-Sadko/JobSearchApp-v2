package com.epam.esm.exception;

public class ElementCanNotBeDeletedException extends RuntimeException {

    public ElementCanNotBeDeletedException(String message) {
        super(message);
    }

    public ElementCanNotBeDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementCanNotBeDeletedException(Throwable cause) {
        super(cause);
    }
}
