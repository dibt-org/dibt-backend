package com.kim.dibt.core.utils.business;

import org.springframework.util.ReflectionUtils;

import java.util.Map;

//! This class is used to update an object with a map of values
//TODO: This class is not tested yet
public class PatchUpdate {
    public static <T> void update(T oldObject, Map<String, Object> newObject) {
        newObject.forEach((key, value) -> {
            var field = ReflectionUtils.findField(oldObject.getClass(), key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, oldObject, value);
            }
        });
    }
}
