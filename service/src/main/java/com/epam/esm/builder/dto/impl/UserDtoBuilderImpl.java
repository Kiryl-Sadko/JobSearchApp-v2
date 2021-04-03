package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.UserDtoBuilder;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public class UserDtoBuilderImpl implements UserDtoBuilder {

    private UserDto userDto = new UserDto();

    @Override
    public UserDto build() {
        UserDto result = new UserDto();
        result.setId(userDto.getId());
        result.setName(userDto.getName());
        result.setRoleDtoList(userDto.getRoleDtoList());
        result.setApplicationDtoList(userDto.getApplicationDtoList());
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
    public UserDtoBuilder setRoleDtoList(List<RoleDto> roleDtoList) {
        userDto.setRoleDtoList(roleDtoList);
        return this;
    }

    @Override
    public UserDtoBuilder setJobApplicationDtoList(List<JobApplicationDto> jobApplicationDtoList) {
        userDto.setApplicationDtoList(jobApplicationDtoList);
        return this;
    }
}
