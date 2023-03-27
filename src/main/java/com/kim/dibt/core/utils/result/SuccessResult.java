package com.kim.dibt.core.utils.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SuccessResult extends Result {

    public SuccessResult(String message) {
        super(message, true);
    }

    public SuccessResult() {
        super(true);
    }
}
