package com.epam.esm.service.impl;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ElementCanNotBeDeletedException;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.exception.SuchElementAlreadyExistsException;
import com.epam.esm.repository.JobApplicationRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
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
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.service.Utils.validate;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserConverter converter;
    private final Validator validator;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JobApplicationRepository jobApplicationRepository, UserConverter converter, Validator validator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jobApplicationRepository = jobApplicationRepository;
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
    public List<UserDto> findAll() {
        List<User> all = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        all.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.orElseThrow();
            return converter.convertToDto(user);
        }
        String message = "The entity not found";
        LOGGER.error(message);
        throw new NoSuchElementException(message);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.orElseThrow();
            Set<JobApplication> jobApplications = user.getJobApplications();
            if (CollectionUtils.isEmpty(jobApplications)) {
                userRepository.deleteById(id);
                LOGGER.info("User by id={} has deleted", id);
            } else {
                String message = MessageFormat.
                        format("User by id={0} cannot be deleted, it is used at another element of application", id);
                LOGGER.error(message);
                throw new ElementCanNotBeDeletedException(message);
            }
        } else {
            String message = MessageFormat.format("User with id={0} not found", id);
            LOGGER.error(message);
            throw new ElementCanNotBeDeletedException(message);
        }
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Entity with id={0} not found.");
            Long id = save(dto);
            return findById(id);
        }
        validate(dto, validator);
        User user = converter.convertToEntity(dto);
        User entity = userRepository.save(user);
        return converter.convertToDto(entity);
    }

    @Override
    @Transactional
    public UserDto partialUpdate(UserDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id, id should not be null");
            throw new InvalidDtoException("Invalid id, id should not be null");
        }
        User user = userRepository.findById(dto.getId()).orElseThrow();
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        updateRoleList(dto, user);
        updateJobApplicationList(dto);
        user = userRepository.save(user);
        return converter.convertToDto(user);
    }

    private void updateRoleList(UserDto dto, User userResult) {
        Long userId = dto.getId();
        List<Long> roleIdList = dto.getRoleIdList();
        if (!CollectionUtils.isEmpty(roleIdList)) {
            List<Role> roleList = roleRepository.findAllById(roleIdList);
            Set<Role> roleFromUI = new HashSet<>(roleList);
            User userFromDB = userRepository.findById(userId).orElseThrow();
            Set<Role> rolesFromDB = userFromDB.getRoles();

            rolesFromDB.removeIf(dbRole -> {
                if (roleFromUI.stream().noneMatch(dbRole::equals)) {
                    Set<User> dbUser = dbRole.getUsers();
                    dbUser.removeIf(user -> user.getId().equals(userId));
                    return true;
                }
                return false;
            });

            roleFromUI.removeIf(uiRole ->
                    rolesFromDB.stream().anyMatch(role -> role.equals(uiRole)));

            roleFromUI.forEach(role -> role.addUser(userResult));

            rolesFromDB.addAll(roleFromUI);
            userResult.setRoles(rolesFromDB);
        }
    }

    private void updateJobApplicationList(UserDto dto) {
        User user = converter.convertToEntity(dto);
        List<Long> jobApplicationIdList = dto.getJobApplicationIdList();
        if (!CollectionUtils.isEmpty(jobApplicationIdList)) {
            List<JobApplication> jobApplicationList = jobApplicationRepository.findAllById(jobApplicationIdList);
            jobApplicationList.forEach(jobApplication -> jobApplication.setUser(user));
            List<JobApplication> listFromDB = jobApplicationRepository.findByUserId(dto.getId());

            listFromDB.forEach(jobApplication -> {
                if (!jobApplicationList.contains(jobApplication)) {
                    Long id = jobApplication.getId();
                    jobApplicationRepository.deleteById(id);
                    LOGGER.info("Job application with id = {} has been deleted", id);
                }
            });
        }
    }

    @Override
    @Transactional
    public Long save(UserDto dto) {
        validate(dto, validator);
        User entity = converter.convertToEntity(dto);
        Long id = userRepository.findTopByOrderByIdDesc().getId();
        entity.setId(++id);
        try {
            entity = userRepository.save(entity);
        } catch (RuntimeException exception) {
            String message = MessageFormat
                    .format("The user already exists: {0}.", entity);
            LOGGER.error(message);
            throw new SuchElementAlreadyExistsException(message);
        }
        LOGGER.info("The user id={} has been saved in the database", entity.getId());
        return entity.getId();
    }
}
