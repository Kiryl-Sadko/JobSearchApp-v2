package com.epam.esm.service.impl;

import com.epam.esm.converter.SkillConverter;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.exception.ElementCanNotBeDeletedException;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.exception.SuchElementAlreadyExistsException;
import com.epam.esm.repository.SkillRepository;
import com.epam.esm.repository.VacancyRepository;
import com.epam.esm.service.SkillService;
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
public class SkillServiceImpl implements SkillService {

    private static final Logger LOGGER = LogManager.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;
    private final VacancyRepository vacancyRepository;
    private final SkillConverter converter;
    private final Validator validator;

    public SkillServiceImpl(SkillRepository skillRepository,
                            VacancyRepository vacancyRepository, SkillConverter converter, Validator validator) {
        this.skillRepository = skillRepository;
        this.vacancyRepository = vacancyRepository;
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
    public List<SkillDto> findAll() {
        List<Skill> all = skillRepository.findAll();
        List<SkillDto> result = new ArrayList<>();
        all.forEach(entity -> result.add(converter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public SkillDto findById(Long id) {
        Optional<Skill> optional = skillRepository.findById(id);
        if (optional.isPresent()) {
            Skill skill = optional.get();
            return converter.convertToDto(skill);
        }
        String message = "The entity not found";
        LOGGER.error(message);
        throw new NoSuchElementException(message);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {
            Skill skill = optionalSkill.get();
            Set<Vacancy> vacancies = skill.getVacancies();
            if (CollectionUtils.isEmpty(vacancies)) {
                skillRepository.deleteById(id);
                LOGGER.info("Skill by id={} has deleted", id);
            } else {
                String message = MessageFormat.
                        format("Skill by id={0} cannot be deleted, it is used at another element of application", id);
                LOGGER.error(message);
                throw new ElementCanNotBeDeletedException(message);
            }
        } else {
            String message = MessageFormat.format("Skill with id={0} not found", id);
            LOGGER.error(message);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    @Transactional
    public SkillDto partialUpdate(SkillDto dto) {
        Long dtoId = dto.getId();
        if (dtoId == null) {
            LOGGER.error("Invalid id, id should not be null");
            throw new InvalidDtoException("Invalid id, id should not be null");
        }

        Skill result = skillRepository.findById(dtoId).orElseThrow();
        updateName(dto, result);
        updateVacancyList(dto, result);

        result = skillRepository.save(result);
        return converter.convertToDto(result);
    }

    @Override
    @Transactional
    public SkillDto update(SkillDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Entity with id={0} not found. The Skill will be created instead of updating");
            Long id = this.save(dto);
            return findById(id);
        }
        validate(dto, validator);
        Skill skill = converter.convertToEntity(dto);
        updateVacancyList(dto, skill);
        skill = skillRepository.save(skill);
        return converter.convertToDto(skill);
    }

    private void updateVacancyList(SkillDto dto, Skill skillResult) {
        Long skillId = dto.getId();
        List<Long> vacancyIdList = dto.getVacancyIdList();
        if (!CollectionUtils.isEmpty(vacancyIdList)) {
            List<Vacancy> vacancyList = vacancyRepository.findAllById(vacancyIdList);
            Set<Vacancy> vacanciesFromUI = new HashSet<>(vacancyList);
            Skill skillFromDB = skillRepository.findById(skillId).orElseThrow();
            Set<Vacancy> vacanciesFromDB = skillFromDB.getVacancies();
            //удаление Вакансий из списка базы данных, которе не перечислены пользователем
            vacanciesFromDB.removeIf(dbVacancy -> {
                if (vacanciesFromUI.stream().noneMatch(dbVacancy::equals)) {
                    Set<Skill> dbSkills = dbVacancy.getSkills();
                    dbSkills.removeIf(skill -> skill.getId().equals(skillId));
                    return true;
                }
                return false;
            });
            //удаление Вакансий из списка пользователя, если такие уже записаны в базу
            vacanciesFromUI.removeIf(uiVacancy ->
                    vacanciesFromDB.stream().anyMatch(v -> uiVacancy.getId().equals(v.getId())));
            //установить обновленный скилл на все новые вакансии
            vacanciesFromUI.forEach(vacancy -> vacancy.addSkill(skillResult));

            vacanciesFromDB.addAll(vacanciesFromUI);
            skillResult.setVacancies(vacanciesFromDB);
        }
    }

    @Override
    @Transactional
    public Long save(SkillDto dto) {
        validate(dto, validator);
        Skill skill = converter.convertToEntity(dto);
        checkDtoForExistence(skill);
        try {
            skill = skillRepository.save(skill);
        } catch (RuntimeException exception) {
            String message = MessageFormat
                    .format("The skill with name = {0} already exists.", skill.getName());
            LOGGER.error(message);
            throw new SuchElementAlreadyExistsException(message);
        }
        LOGGER.info("The skill id={} has been saved in the database", skill.getId());

        return skill.getId();
    }

    private void checkDtoForExistence(Skill skill) {
        Long skillId = skill.getId();
        if (skillId != null) {
            Optional<Skill> optional = skillRepository.findById(skillId);
            if (optional.isPresent()) {
                String message = MessageFormat
                        .format("The skill with id = {0} already exists.", skillId);
                LOGGER.error(message);
                throw new SuchElementAlreadyExistsException(message);
            }
        }
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

    private void updateName(SkillDto dto, Skill result) {
        String dtoName = dto.getName();
        Optional<Skill> optionalByName = skillRepository.findByName(dtoName);
        if (dtoName != null && optionalByName.isEmpty()) {
            result.setName(dto.getName());

        } else if (optionalByName.isPresent() && !optionalByName.get().equals(result)) {
            String message = MessageFormat.format("Skill with name = {0} already exists", dtoName);
            LOGGER.error(message);
            throw new SuchElementAlreadyExistsException(message);
        }
    }
}
