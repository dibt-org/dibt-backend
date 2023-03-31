package com.kim.dibt.core.utils.result;

public abstract class IResult {
    private boolean success;
    private String message;

    public IResult(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public IResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;

    }

    public String getMessage() {
        return message;
    }
}
