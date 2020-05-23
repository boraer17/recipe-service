package com.rs.exception;



public abstract class AbstractRuntimeException extends RuntimeException {
    protected AbstractRuntimeException(String message) {
        super(message);
    }

    protected AbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AbstractRuntimeException(Throwable cause) {
        super(cause);
    }

    protected AbstractRuntimeException(String message, Object...parameters) {
        super(String.format(message, parameters));
    }

}
