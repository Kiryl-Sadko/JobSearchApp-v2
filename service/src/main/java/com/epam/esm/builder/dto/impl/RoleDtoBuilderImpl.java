package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.RoleDtoBuilder;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public class RoleDtoBuilderImpl implements RoleDtoBuilder {

    private RoleDto roleDto = new RoleDto();

    @Override
    public RoleDto build() {
        RoleDto result = new RoleDto();
        result.setId(roleDto.getId());
        result.setName(roleDto.getName());
        result.setUserDtoList(roleDto.getUserDtoList());
        this.reset();
        return result;
    }

    @Override
    public RoleDtoBuilder reset() {
        roleDto = new RoleDto();
        return this;
    }

    @Override
    public RoleDtoBuilder setId(Long id) {
        roleDto.setId(id);
        return this;
    }

    @Override
    public RoleDtoBuilder setName(String name) {
        roleDto.setName(name);
        return this;
    }

    @Override
    public RoleDtoBuilder setUserDtoList(List<UserDto> userDtoList) {
        roleDto.setUserDtoList(userDtoList);
        return this;
    }
}
