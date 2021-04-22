package com.epam.esm.service.impl;

import com.epam.esm.converter.RoleConverter;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final RoleConverter converter;
    private final Validator validator;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter converter, Validator validator) {
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<RoleDto> findAll(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        List<Role> roles = page.getContent();

        List<RoleDto> result = new ArrayList<>();
        roles.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow();
        return converter.convertToDto(role);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            List<User> users = role.getUsers();
            if (users.isEmpty()) {
                roleRepository.deleteById(id);
                LOGGER.info("Role by id={} has deleted", id);
                return true;
            }
            LOGGER.info("Role by id={} cannot be deleted", id);
            return false;
        }
        LOGGER.info("User by id={} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public RoleDto update(RoleDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id was entered");
            throw new InvalidDtoException("Invalid id was entered");
        }
        Optional<Role> optional = roleRepository.findById(dto.getId());
        Role role = optional.orElseThrow();
        if (dto.getName() != null) {
            role.setName(dto.getName());
        }
        if (dto.getUserIdList() != null) {
            List<User> userList = converter.convertToEntity(dto).getUsers();
            role.setUsers(userList);
        }
        role = roleRepository.save(role);
        return converter.convertToDto(role);
    }

    @Override
    @Transactional
    public Long save(RoleDto dto) {
        Set<ConstraintViolation<RoleDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            Role entity = converter.convertToEntity(dto);
            entity = roleRepository.save(entity);
            LOGGER.info("The DTO id={} has been saved in the database", entity.getId());
            return entity.getId();
        } else {
            String message = MessageFormat.format("This dto {0} is invalid, check violations: {1}", dto, violations);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
