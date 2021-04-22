package com.epam.esm.converter.impl;

import com.epam.esm.builder.dto.RoleDtoBuilder;
import com.epam.esm.builder.entity.RoleBuilder;
import com.epam.esm.converter.RoleConverter;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleConverterImpl implements RoleConverter {

    private final UserRepository userRepository;
    private final RoleBuilder entityBuilder;
    private final RoleDtoBuilder dtoBuilder;

    public RoleConverterImpl(UserRepository userRepository, RoleBuilder entityBuilder, RoleDtoBuilder dtoBuilder) {
        this.userRepository = userRepository;
        this.entityBuilder = entityBuilder;
        this.dtoBuilder = dtoBuilder;
    }

    @Override
    public Role convertToEntity(RoleDto dto) {
        List<Long> userIdList = dto.getUserIdList();
        List<User> users = userRepository.findAllById(userIdList);

        return entityBuilder.setId(dto.getId())
                .setName(dto.getName())
                .setUsers(users)
                .build();
    }

    @Override
    public RoleDto convertToDto(Role dto) {
        List<User> users = dto.getUsers();
        List<Long> userIdList = new ArrayList<>();
        users.forEach(user -> userIdList.add(user.getId()));

        return dtoBuilder.setId(dto.getId())
                .setName(dto.getName())
                .setUserIdList(userIdList)
                .build();
    }
}
