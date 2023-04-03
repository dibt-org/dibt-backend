package com.kim.dibt.exceptions;

import java.io.IOException;

public class CustomIOException extends IOException {
    public CustomIOException(String message) {
        super(message);
    }
    public CustomIOException(Throwable cause) {
        super(cause);
    }
    public CustomIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
