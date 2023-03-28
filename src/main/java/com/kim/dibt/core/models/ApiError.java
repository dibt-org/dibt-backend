package com.kim.dibt.core.models;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiError {
    private String message;
    private String code;
    private Map<String, String> errors = new HashMap<>();
}
