package com.kim.dibt.exceptions;

public class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(Throwable cause) {
        super(cause);
    }

}
