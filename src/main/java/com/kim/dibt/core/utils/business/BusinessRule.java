package com.kim.dibt.core.utils.business;

import com.kim.dibt.core.utils.result.Result;

public class BusinessRule {
    public static Result run(Result... logics) {
        for (var logic : logics) {
            if (!logic.isSuccess())
                return logic;
        }
        return null;
    }


}
