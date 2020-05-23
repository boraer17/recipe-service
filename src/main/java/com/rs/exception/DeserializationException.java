package com.rs.exception;

public class DeserializationException extends AbstractRuntimeException {
    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(String message, Object... parameters) {
        super(message, parameters);
    }
}
