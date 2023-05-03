package com.kim.dibt.core.utils.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SuccessDataResult<T> extends DataResult<T> {

    public SuccessDataResult(T data, String message) {
        super(data, message, true);
    }

    public SuccessDataResult(T data) {
        super(data, true);
    }

    public SuccessDataResult(String message) {
        super(null, message, true);
    }

    public SuccessDataResult() {
        super(null, true);
    }

    public static <T> SuccessDataResult<T> of(T data, String message) {
        return new SuccessDataResult<>(data, message);
    }
    public static <T> SuccessDataResult<T> of(T data) {
        return new SuccessDataResult<>(data);
    }

}
