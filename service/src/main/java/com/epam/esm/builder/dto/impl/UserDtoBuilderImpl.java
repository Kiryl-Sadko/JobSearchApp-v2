package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.UserDtoBuilder;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoBuilderImpl implements UserDtoBuilder {

    private UserDto userDto = new UserDto();

    @Override
    public UserDto build() {
        UserDto result = new UserDto();
        result.setId(userDto.getId());
        result.setName(userDto.getName());
        result.setRoleIdList(userDto.getRoleIdList());
        result.setApplicationIdList(userDto.getJobApplicationIdList());
        this.reset();
        return result;
    }

    @Override
    public UserDtoBuilder reset() {
        userDto = new UserDto();
        return this;
    }

    @Override
    public UserDtoBuilder setId(Long id) {
        userDto.setId(id);
        return this;
    }

    @Override
    public UserDtoBuilder setName(String name) {
        userDto.setName(name);
        return this;
    }

    @Override
    public UserDtoBuilder setPassword(String password) {
        userDto.setPassword(password);
        return this;
    }

    @Override
    public UserDtoBuilder setRoleIdList(List<Long> roleIdList) {
        userDto.setRoleIdList(roleIdList);
        return this;
    }

    @Override
    public UserDtoBuilder setJobApplicationIdList(List<Long> jobApplicationIdList) {
        userDto.setApplicationIdList(jobApplicationIdList);
        return this;
    }
}
