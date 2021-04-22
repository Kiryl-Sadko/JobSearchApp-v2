package com.epam.esm.builder.dto;

import com.epam.esm.dto.RoleDto;

import java.util.List;

public interface RoleDtoBuilder extends DtoBuilder<RoleDto, RoleDtoBuilder> {

    RoleDtoBuilder setName(String name);

    RoleDtoBuilder setUserIdList(List<Long> userIdList);
}
