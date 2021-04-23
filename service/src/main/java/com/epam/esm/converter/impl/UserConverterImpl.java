package com.epam.esm.converter.impl;

import com.epam.esm.builder.dto.UserDtoBuilder;
import com.epam.esm.builder.entity.UserBuilder;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.JobApplicationRepository;
import com.epam.esm.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleRepository roleRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserBuilder entityBuilder;
    private final UserDtoBuilder dtoBuilder;

    public UserConverterImpl(RoleRepository roleRepository,
                             JobApplicationRepository jobApplicationRepository,
                             UserBuilder entityBuilder,
                             UserDtoBuilder dtoBuilder) {
        this.roleRepository = roleRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.entityBuilder = entityBuilder;
        this.dtoBuilder = dtoBuilder;
    }

    @Override
    public User convertToEntity(UserDto dto) {
        List<Long> roleIdList = dto.getRoleIdList();
        List<Role> roles = roleRepository.findAllById(roleIdList);
        List<Long> jobApplicationIdList = dto.getJobApplicationIdList();
        List<JobApplication> jobApplications = jobApplicationRepository.findAllById(jobApplicationIdList);

        return entityBuilder.setId(dto.getId())
                .setName(dto.getName())
                .setPassword(dto.getPassword())
                .setRoles(roles)
                .setJobApplications(jobApplications)
                .build();
    }

    @Override
    public UserDto convertToDto(User entity) {
        List<Role> roles = entity.getRoles();
        List<Long> roleIdList = new ArrayList<>();
        roles.forEach(role -> roleIdList.add(role.getId()));

        List<JobApplication> jobApplications = entity.getJobApplications();
        List<Long> jobApplicationIdList = new ArrayList<>();
        jobApplications.forEach(jobApplication -> jobApplicationIdList.add(jobApplication.getId()));

        return dtoBuilder.setId(entity.getId())
                .setName(entity.getName())
                .setRoleIdList(roleIdList)
                .setJobApplicationIdList(jobApplicationIdList)
                .build();
    }
}
