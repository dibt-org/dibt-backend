package com.kim.dibt.core.utils.business;

import com.kim.dibt.core.utils.result.*;

public class BusinessRule {
    public static IResult run(IResult... logics) {
        for (var logic : logics) {
            if (!logic.isSuccess())
                return logic;
        }
        return null;
    }
}
