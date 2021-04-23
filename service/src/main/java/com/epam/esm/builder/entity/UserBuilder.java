package com.epam.esm.builder.entity;

import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserBuilder extends EntityBuilder<User, UserBuilder> {

    UserBuilder setName(String name);

    UserBuilder setPassword(String password);

    UserBuilder setRoles(List<Role> roles);

    UserBuilder setJobApplications(List<JobApplication> jobApplications);
}
