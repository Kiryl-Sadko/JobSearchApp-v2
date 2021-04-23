package com.epam.esm.service.impl;

import com.epam.esm.converter.SkillConverter;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.repository.SkillRepository;
import com.epam.esm.service.SkillService;
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
public class SkillServiceImpl implements SkillService {

    private static final Logger LOGGER = LogManager.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;
    private final SkillConverter converter;
    private final Validator validator;

    public SkillServiceImpl(SkillRepository skillRepository,
                            SkillConverter converter, Validator validator) {
        this.skillRepository = skillRepository;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<SkillDto> findAll(Pageable pageable) {
        Page<Skill> skillPage = skillRepository.findAll(pageable);
        List<Skill> skillList = skillPage.getContent();

        List<SkillDto> result = new ArrayList<>();
        skillList.forEach(skill -> result.add(converter.convertToDto(skill)));
        return result;
    }

    @Override
    @Transactional
    public SkillDto findById(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow();
        return converter.convertToDto(skill);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {
            Skill skill = optionalSkill.get();
            List<Vacancy> vacancies = skill.getVacancies();
            if (vacancies.isEmpty()) {
                skillRepository.deleteById(id);
                LOGGER.info("Skill by id={} has deleted", id);
                return true;
            }
            LOGGER.info("Skill by id={} cannot be deleted", id);
            return false;
        }
        LOGGER.info("User by id={} does not exist", id);
        return false;
    }

    @Override
    @Transactional
    public SkillDto update(SkillDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Invalid id was entered");
            throw new InvalidDtoException("Invalid id was entered");
        }
        Optional<Skill> optional = skillRepository.findById(dto.getId());
        Skill skill = optional.orElseThrow();
        if (dto.getName() != null) {
            skill.setName(dto.getName());
        }
        if (dto.getVacancyIdList() != null) {
            List<Vacancy> vacancies = converter.convertToEntity(dto).getVacancies();
            skill.setVacancies(vacancies);
        }
        skill = skillRepository.save(skill);
        return converter.convertToDto(skill);
    }

    @Override
    @Transactional
    public Long save(SkillDto dto) {
        validate(dto);
        Skill entity = converter.convertToEntity(dto);
        entity = skillRepository.save(entity);
        LOGGER.info("The DTO id={} has been saved in the database", entity.getId());
        return entity.getId();

    }

    @Override
    @Transactional
    public List<SkillDto> findByName(String name, Pageable pageable) {
        List<Skill> skillList = skillRepository.findByNameStartsWith(name, pageable).getContent();
        List<SkillDto> result = new ArrayList<>();
        skillList.forEach(skill -> {
            SkillDto dto = converter.convertToDto(skill);
            result.add(dto);
        });
        return result;
    }

    @Override
    @Transactional
    public SkillDto findTheMostWidelyUsedSkillWithHighestSalary() {
        Skill skill = skillRepository.findTheMostWidelyUsedSkillWithHighestSalary().orElseThrow();
        return converter.convertToDto(skill);
    }

    private void validate(SkillDto dto) {
        Set<ConstraintViolation<SkillDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String message = MessageFormat.format("This dto {0} is invalid, check violations: {1}", dto, violations);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
