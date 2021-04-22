package com.epam.esm.service.impl;

import com.epam.esm.converter.JobApplicationConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.converter.VacancyConverter;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.User;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.repository.JobApplicationRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.VacancyRepository;
import com.epam.esm.service.JobApplicationService;
import com.epam.esm.service.Utils;
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
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private static final Logger LOGGER = LogManager.getLogger(JobApplicationServiceImpl.class);

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final JobApplicationConverter converter;
    private final UserConverter userConverter;
    private final VacancyConverter vacancyConverter;
    private final Validator validator;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     UserRepository userRepository,
                                     VacancyRepository vacancyRepository,
                                     JobApplicationConverter converter,
                                     UserConverter userConverter,
                                     VacancyConverter vacancyConverter, Validator validator) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.vacancyRepository = vacancyRepository;
        this.converter = converter;
        this.userConverter = userConverter;
        this.vacancyConverter = vacancyConverter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<JobApplicationDto> findAll(Pageable pageable) {
        Page<JobApplication> page = jobApplicationRepository.findAll(pageable);
        List<JobApplication> jobApplicationList = page.getContent();

        List<JobApplicationDto> result = new ArrayList<>();
        jobApplicationList.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public JobApplicationDto findById(Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id).orElseThrow();
        return converter.convertToDto(jobApplication);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<JobApplication> optional = jobApplicationRepository.findById(id);
        if (optional.isPresent()) {
            jobApplicationRepository.deleteById(id);
            LOGGER.info("JobApplication by id={} has deleted", id);
            return true;
        }
        LOGGER.info("JobApplication by id={} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public JobApplicationDto update(JobApplicationDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id was entered");
            throw new InvalidDtoException("Invalid id was entered");
        }
        Optional<JobApplication> optional = jobApplicationRepository.findById(dto.getId());
        JobApplication jobApplication = optional.orElseThrow();
        if (dto.getSalary() != null) {
            jobApplication.setSalary(dto.getSalary());
        }
        if (dto.getResponseDate() != null) {
            jobApplication.setResponseDate(Utils.getCalendarFromString(dto.getResponseDate()));
        }
        if (dto.getUserDto() != null) {
            jobApplication.setUser(userConverter.convertToEntity(dto.getUserDto()));
        }
        if (dto.getVacancyDto() != null) {
            jobApplication.setVacancy(vacancyConverter.convertToEntity(dto.getVacancyDto()));
        }
        jobApplication = jobApplicationRepository.save(jobApplication);
        return converter.convertToDto(jobApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public Long save(JobApplicationDto dto) {
        Set<ConstraintViolation<JobApplicationDto>> violations = validator.validate(dto);
        if (violations.isEmpty()) {
            JobApplication entity = converter.convertToEntity(dto);
            entity = jobApplicationRepository.save(entity);
            LOGGER.info("The DTO id={} has been saved in the database", entity.getId());
            return entity.getId();
        } else {
            String message = MessageFormat.format("This dto {0} is invalid, check violations: {1}", dto, violations);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }

    @Override
    @Transactional
    public List<JobApplicationDto> findByUserId(Long userId, Pageable pageable) {
        List<JobApplication> jobApplications = jobApplicationRepository.findByUserId(userId, pageable).getContent();
        List<JobApplicationDto> result = new ArrayList<>();
        jobApplications.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public JobApplicationDto create(Long userId, Long vacancyId) {
        User user = userRepository.findById(userId).orElseThrow();
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();

        JobApplication jobApplication = new JobApplication();
        jobApplication.setUser(user);
        jobApplication.setVacancy(vacancy);
        jobApplication.setSalary(vacancy.getSalary());
        jobApplication.setResponseDate(Calendar.getInstance());
        jobApplication = jobApplicationRepository.save(jobApplication);
        return converter.convertToDto(jobApplication);
    }
}
