package com.epam.esm.builder.entity;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.util.List;

public interface RoleBuilder extends EntityBuilder<Role, RoleBuilder> {

    RoleBuilder setName(String name);

    RoleBuilder setUsers(List<User> users);
}
