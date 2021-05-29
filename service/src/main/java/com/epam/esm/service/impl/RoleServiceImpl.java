package com.epam.esm.service.impl;

import com.epam.esm.converter.RoleConverter;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ElementCanNotBeDeletedException;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.exception.SuchElementAlreadyExistsException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.service.Utils.validate;

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
    public List<RoleDto> findAll() {
        List<Role> all = roleRepository.findAll();
        List<RoleDto> result = new ArrayList<>();
        all.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public RoleDto findById(Long id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            Role role = optional.orElseThrow();
            return converter.convertToDto(role);
        }
        String message = "The entity not found";
        LOGGER.error(message);
        throw new NoSuchElementException(message);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.orElseThrow();
            Set<User> users = role.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                roleRepository.deleteById(id);
                LOGGER.info("Role by id={} has deleted", id);
            } else {
                String message = MessageFormat.
                        format("Role by id={0} cannot be deleted, it is used at another element of application", id);
                LOGGER.error(message);
                throw new ElementCanNotBeDeletedException(message);
            }
        } else {
            String message = MessageFormat.format("Role with id={0} not found", id);
            LOGGER.error(message);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    @Transactional
    public RoleDto partialUpdate(RoleDto dto) {
        if (dto.getId() == null) {
            LOGGER.error("Invalid id, id should not be null");
            throw new InvalidDtoException("Invalid id, id should not be null");
        }
        Role role = roleRepository.findById(dto.getId()).orElseThrow();
        if (dto.getName() != null) {
            role.setName(dto.getName());
        }
        if (dto.getUserIdList() != null) {
            Set<User> userList = converter.convertToEntity(dto).getUsers();
            role.setUsers(userList);
        }
        role = roleRepository.save(role);
        return converter.convertToDto(role);
    }

    @Override
    @Transactional
    public RoleDto update(RoleDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Entity with id={0} not found.");
            Long id = save(dto);
            return findById(id);
        }
        validate(dto, validator);
        Role role = converter.convertToEntity(dto);
        Role entity = roleRepository.save(role);
        return converter.convertToDto(entity);
    }

    @Override
    @Transactional
    public Long save(RoleDto dto) {
        validate(dto, validator);
        Role entity = converter.convertToEntity(dto);
        Long id = roleRepository.findTopByOrderByIdDesc().getId();
        entity.setId(++id);

        try {
            entity = roleRepository.save(entity);
        } catch (RuntimeException exception) {
            String message = MessageFormat
                    .format("The role already exists: {0}.", entity);
            LOGGER.error(message);
            throw new SuchElementAlreadyExistsException(message);
        }
        LOGGER.info("The role id={} has been saved in the database", entity.getId());
        return entity.getId();
    }
}
