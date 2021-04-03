package com.epam.esm.builder.dto;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface RoleDtoBuilder extends DtoBuilder<RoleDto, RoleDtoBuilder> {

    RoleDtoBuilder setName(String name);

    RoleDtoBuilder setUserDtoList(List<UserDto> userDtoList);
}
