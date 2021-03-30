package com.epam.esm.builder.entity.impl;

import com.epam.esm.builder.entity.UserBuilder;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.util.List;

public class UserBuilderImpl implements UserBuilder {

    private User user = new User();

    @Override
    public User build() {
        User result = new User();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setPassword(user.getPassword());
        result.setRoles(user.getRoles());
        result.setJobApplications(user.getJobApplications());
        this.reset();
        return result;
    }

    @Override
    public UserBuilder reset() {
        user = new User();
        return this;
    }

    @Override
    public UserBuilder setId(Long id) {
        user.setId(id);
        return this;
    }

    @Override
    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }

    @Override
    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    @Override
    public UserBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }

    @Override
    public UserBuilder setJobApplications(List<JobApplication> jobApplications) {
        user.setJobApplications(jobApplications);
        return this;
    }
}
