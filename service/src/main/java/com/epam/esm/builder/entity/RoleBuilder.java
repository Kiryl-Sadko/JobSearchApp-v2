package com.epam.esm.builder.entity;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.util.Set;

public interface RoleBuilder extends EntityBuilder<Role, RoleBuilder> {

    RoleBuilder setName(String name);

    RoleBuilder setUsers(Set<User> users);
}
