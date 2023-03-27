package com.kim.dibt.core.utils.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Result {
    private String message;
    private boolean success;


    protected Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    protected Result(boolean success) {
        this.success = success;
        this.message = "success";
    }
}
