package com.epam.esm.builder.dto;

import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserDtoBuilder extends DtoBuilder<UserDto, UserDtoBuilder> {

    UserDtoBuilder setName(String name);

    UserDtoBuilder setRoleDtoList(List<RoleDto> roleDtoList);

    UserDtoBuilder setJobApplicationDtoList(List<JobApplicationDto> jobApplicationDtoList);
}
