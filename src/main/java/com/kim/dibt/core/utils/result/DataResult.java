package com.kim.dibt.core.utils.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DataResult<T> extends Result {
    private final T data;

    protected DataResult(T data, String message, boolean success) {
        super(message, success);
        this.data = data;
    }

    protected DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }
}
