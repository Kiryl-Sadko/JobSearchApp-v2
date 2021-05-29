package com.epam.esm.builder.entity.impl;

import com.epam.esm.builder.entity.RoleBuilder;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleBuilderImpl implements RoleBuilder {

    private Role role = new Role();

    @Override
    public Role build() {
        Role result = new Role();
        result.setId(role.getId());
        result.setName(role.getName());
        result.setUsers(role.getUsers());
        this.reset();
        return result;
    }

    @Override
    public RoleBuilder reset() {
        role = new Role();
        return this;
    }

    @Override
    public RoleBuilder setId(Long id) {
        role.setId(id);
        return this;
    }

    @Override
    public RoleBuilder setName(String name) {
        role.setName(name);
        return this;
    }

    @Override
    public RoleBuilder setUsers(Set<User> users) {
        role.setUsers(users);
        return this;
    }
}
