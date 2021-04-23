package com.epam.esm.service.impl;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final Validator validator;

    public UserServiceImpl(UserRepository userRepository, UserConverter converter, Validator validator) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<UserDto> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserDto> result = new ArrayList<>();
        users.forEach(user ->
                result.add(converter.convertToDto(user)));
        return result;
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return converter.convertToDto(user);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<JobApplication> jobApplications = user.getJobApplications();
            if (CollectionUtils.isEmpty(jobApplications)) {
                userRepository.deleteById(id);
                LOGGER.info("User by id={} has deleted", id);
                return true;
            }
            LOGGER.info("User by id={} cannot be deleted", id);
            return false;
        }
        LOGGER.info("User by id={} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id was entered");
            throw new InvalidDtoException("Invalid id was entered");
        }
        Optional<User> optional = userRepository.findById(dto.getId());
        User user = optional.orElseThrow();
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getRoleIdList() != null) {
            List<Role> roles = converter.convertToEntity(dto).getRoles();
            user.setRoles(roles);
        }
        if (dto.getJobApplicationIdList() != null) {
            List<JobApplication> jobApplications = converter.convertToEntity(dto).getJobApplications();
            user.setJobApplications(jobApplications);
        }
        user = userRepository.save(user);
        return converter.convertToDto(user);
    }

    @Override
    @Transactional
    public Long save(UserDto dto) {
        validate(dto);
        User entity = converter.convertToEntity(dto);
        entity = userRepository.save(entity);
        LOGGER.info("The DTO id={} has been saved in the database", entity.getId());
        return entity.getId();
    }

    private void validate(UserDto dto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String message = MessageFormat.format("This dto {0} is invalid, check violations: {1}", dto, violations);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
