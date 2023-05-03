package com.kim.dibt.security.models;

public enum RoleType {
    USER,
    ADMIN,
    SUPER_ADMIN,
    MODERATOR,
    GUEST,
    ANONYMOUS;

    public static boolean contains(String roleType) {
        for (RoleType role : RoleType.values()) {
            if (role.name().equals(roleType)) {
                return true;
            }
        }
        return false;
    }
}
