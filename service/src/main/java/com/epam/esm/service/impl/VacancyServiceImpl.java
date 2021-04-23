package com.epam.esm.service.impl;

import com.epam.esm.converter.SkillConverter;
import com.epam.esm.converter.VacancyConverter;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.repository.VacancyRepository;
import com.epam.esm.service.Utils;
import com.epam.esm.service.VacancyService;
import com.epam.esm.specification.VacancySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public class VacancyServiceImpl implements VacancyService {

    private static final Logger LOGGER = LogManager.getLogger(VacancyServiceImpl.class);

    private final VacancyRepository vacancyRepository;
    private final VacancyConverter vacancyConverter;
    private final SkillConverter skillConverter;
    private final Validator validator;

    public VacancyServiceImpl(VacancyRepository vacancyRepository,
                              VacancyConverter vacancyConverter, SkillConverter skillConverter, Validator validator) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyConverter = vacancyConverter;
        this.skillConverter = skillConverter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<VacancyDto> findAll(Pageable pageable) {
        Page<Vacancy> page = vacancyRepository.findAll(pageable);
        List<Vacancy> vacancies = page.getContent();
        List<VacancyDto> result = new ArrayList<>();
        vacancies.forEach(vacancy -> result.add(vacancyConverter.convertToDto(vacancy)));
        return result;
    }

    @Override
    @Transactional
    public VacancyDto findById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        return vacancyConverter.convertToDto(vacancy);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get();
            List<JobApplication> jobApplications = vacancy.getJobApplications();
            if (jobApplications.isEmpty()) {
                vacancyRepository.deleteById(id);
                LOGGER.info("Vacancy by id={} has deleted", id);
                return true;
            }
            LOGGER.info("Vacancy by id={} cannot be deleted", id);
            return false;
        }
        LOGGER.info("Vacancy by id={} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public VacancyDto update(VacancyDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id was entered");
            throw new InvalidDtoException("Invalid id was entered");
        }
        Vacancy vacancy = vacancyRepository.findById(dto.getId()).orElseThrow();
        if (dto.getEmployer() != null) {
            vacancy.setEmployer(dto.getEmployer());
        }
        if (dto.getLocation() != null) {
            vacancy.setLocation(dto.getLocation());
        }
        if (dto.getPosition() != null) {
            vacancy.setPosition(dto.getPosition());
        }
        if (dto.getSalary() != null) {
            vacancy.setSalary(dto.getSalary());
        }
        if (dto.getPlacementDate() != null) {
            vacancy.setPlacementDate(Utils.getCalendarFromString(dto.getPlacementDate()));
        }
        if (dto.getSkillIdList() != null) {
            List<Skill> skills = vacancyConverter.convertToEntity(dto).getSkills();
            vacancy.setSkills(skills);
        }
        if (dto.getJobApplicationIdList() != null) {
            List<JobApplication> jobApplications = vacancyConverter.convertToEntity(dto).getJobApplications();
            vacancy.setJobApplications(jobApplications);
        }
        vacancy = vacancyRepository.save(vacancy);
        return vacancyConverter.convertToDto(vacancy);
    }

    @Override
    @Transactional
    public Long save(VacancyDto dto) {
        validate(dto);
        Vacancy entity = vacancyConverter.convertToEntity(dto);
        entity = vacancyRepository.save(entity);
        LOGGER.info("The DTO id={} has been saved in the database", entity.getId());
        return entity.getId();
    }

    @Override
    @Transactional
    public List<VacancyDto> findVacancyBySkill(List<SkillDto> skillDtoList, Pageable pageable) {
        List<Skill> skills = new ArrayList<>();
        skillDtoList.forEach(dto -> skills.add(skillConverter.convertToEntity(dto)));

        List<VacancyDto> result = new ArrayList<>();
        if (!skills.isEmpty()) {
            Specification<Vacancy> specification = VacancySpecification.vacancyContainsSkill(skills.get(0));
            for (int i = 1; i < skills.size(); i++) {
                specification.and(VacancySpecification.vacancyContainsSkill(skills.get(i)));
            }
            List<Vacancy> vacancies = vacancyRepository.findAll(specification, pageable).getContent();
            vacancies.forEach(entity -> result.add(vacancyConverter.convertToDto(entity)));
        }
        return result;
    }

    private void validate(VacancyDto dto) {
        Set<ConstraintViolation<VacancyDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String message = MessageFormat.format("This dto {0} is invalid, check violations: {1}", dto, violations);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
