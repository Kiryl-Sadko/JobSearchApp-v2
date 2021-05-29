package com.epam.esm.builder.dto;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserDtoBuilder extends DtoBuilder<UserDto, UserDtoBuilder> {

    UserDtoBuilder setName(String name);

    UserDtoBuilder setPassword(String password);

    UserDtoBuilder setRoleIdList(List<Long> roleIdList);

    UserDtoBuilder setJobApplicationIdList(List<Long> jobApplicationIdList);
}
