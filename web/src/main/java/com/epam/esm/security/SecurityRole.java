package com.epam.esm.security;

import com.epam.esm.entity.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.security.Permission.*;

public enum SecurityRole {

    ADMIN(Set.of(SKILL_READ, SKILL_WRITE,
            VACANCY_READ, VACANCY_WRITE,
            USER_READ, USER_WRITE,
            JOB_APPLICATION_READ, JOB_APPLICATION_WRITE)),

    USER(Set.of(VACANCY_READ,
            SKILL_READ,
            JOB_APPLICATION_READ, JOB_APPLICATION_WRITE)),

    GUEST(Set.of(VACANCY_READ,
            SKILL_READ,
            JOB_APPLICATION_READ,
            USER_WRITE));

    private final Set<Permission> permissions;

    SecurityRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private static List<SecurityRole> fromRoleList(Set<Role> roles) {
        SecurityRole[] values = SecurityRole.values();
        List<SecurityRole> result = new ArrayList<>();
        for (SecurityRole securityRole : values) {
            result.addAll(roles.stream()
                    .filter(role -> role.getName().equalsIgnoreCase(securityRole.name()))
                    .map(role -> SecurityRole.valueOf(role.getName().toUpperCase()))
                    .collect(Collectors.toList()));
        }

        if (CollectionUtils.isEmpty(result)) {
            result.add(GUEST);
            return result;
        }
        return result;
    }

    public static Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
        List<SecurityRole> securityRoles = fromRoleList(roles);
        Set<SimpleGrantedAuthority> result = new HashSet<>();
        for (SecurityRole securityRole : securityRoles) {
            result.addAll(securityRole.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet()));
        }
        return result;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
