package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.RoleDtoBuilder;
import com.epam.esm.dto.RoleDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDtoBuilderImpl implements RoleDtoBuilder {

    private RoleDto roleDto = new RoleDto();

    @Override
    public RoleDto build() {
        RoleDto result = new RoleDto();
        result.setId(roleDto.getId());
        result.setName(roleDto.getName());
        result.setUserIdList(roleDto.getUserIdList());
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
    public RoleDtoBuilder setUserIdList(List<Long> userIdList) {
        roleDto.setUserIdList(userIdList);
        return this;
    }
}
