package com.epam.esm.security;

public enum Permission {

    VACANCY_READ("vacancy:read"),
    VACANCY_WRITE("vacancy:write"),
    SKILL_READ("skill:read"),
    SKILL_WRITE("skill:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    JOB_APPLICATION_READ("jobApplication:read"),
    JOB_APPLICATION_WRITE("jobApplication:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
