package com.epam.esm.service.impl;

import com.epam.esm.converter.VacancyConverter;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.exception.ElementCanNotBeDeletedException;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.exception.SuchElementAlreadyExistsException;
import com.epam.esm.repository.JobApplicationRepository;
import com.epam.esm.repository.SkillRepository;
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
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.service.Utils.validate;

@Service
public class VacancyServiceImpl implements VacancyService {

    private static final Logger LOGGER = LogManager.getLogger(VacancyServiceImpl.class);

    private final VacancyRepository vacancyRepository;
    private final SkillRepository skillRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final VacancyConverter vacancyConverter;
    private final Validator validator;

    public VacancyServiceImpl(VacancyRepository vacancyRepository,
                              SkillRepository skillRepository, JobApplicationRepository jobApplicationRepository,
                              VacancyConverter vacancyConverter, Validator validator) {
        this.vacancyRepository = vacancyRepository;
        this.skillRepository = skillRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.vacancyConverter = vacancyConverter;
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
    public List<VacancyDto> findAll() {
        List<Vacancy> all = vacancyRepository.findAll();
        List<VacancyDto> result = new ArrayList<>();
        all.forEach(entity -> result.add(vacancyConverter.convertToDto(entity)));
        return result;
    }

    @Override
    @Transactional
    public VacancyDto findById(Long id) {
        Optional<Vacancy> optional = vacancyRepository.findById(id);
        if (optional.isPresent()) {
            Vacancy vacancy = optional.get();
            return vacancyConverter.convertToDto(vacancy);
        }
        String message = "The entity not found";
        LOGGER.error(message);
        throw new NoSuchElementException(message);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get();
            Set<JobApplication> jobApplications = vacancy.getJobApplications();
            if (CollectionUtils.isEmpty(jobApplications)) {
                vacancyRepository.deleteById(id);
                LOGGER.info("Vacancy by id={} has deleted", id);
            } else {
                String message = MessageFormat.
                        format("Vacancy by id={0} cannot be deleted, it is used at another element of application", id);
                LOGGER.error(message);
                throw new ElementCanNotBeDeletedException(message);
            }
        } else {
            String message = MessageFormat.format("Vacancy with id={0} not found", id);
            LOGGER.error(message);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    @Transactional
    public VacancyDto partialUpdate(VacancyDto dto) {
        Long vacancyId = dto.getId();
        if (vacancyId == null) {
            LOGGER.info("Invalid id, id should not be null");
            throw new InvalidDtoException("Invalid id, id should not be null");
        }
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
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
        updateSkillList(dto, vacancy);
        updateJobApplicationList(dto);
        vacancy = vacancyRepository.save(vacancy);
        return vacancyConverter.convertToDto(vacancy);
    }

    @Override
    @Transactional
    public VacancyDto update(VacancyDto dto) {
        if (dto.getId() == null) {
            LOGGER.info("Entity with id={0} not found.");
            Long id = save(dto);
            return findById(id);
        }
        validate(dto, validator);
        Vacancy vacancy = vacancyConverter.convertToEntity(dto);
        updateSkillList(dto, vacancy);
        updateJobApplicationList(dto);
        vacancy = vacancyRepository.save(vacancy);
        return vacancyConverter.convertToDto(vacancy);
    }

    private void updateJobApplicationList(VacancyDto dto) {
        Vacancy vacancy = vacancyConverter.convertToEntity(dto);
        List<Long> jobApplicationIdList = dto.getJobApplicationIdList();
        if (!CollectionUtils.isEmpty(jobApplicationIdList)) {
            List<JobApplication> jobApplicationList = jobApplicationRepository.findAllById(jobApplicationIdList);
            jobApplicationList.forEach(jobApplication -> jobApplication.setVacancy(vacancy));
            List<JobApplication> listFromDB = jobApplicationRepository.findByVacancyId(dto.getId());

            listFromDB.forEach(jobApplication -> {
                if (!jobApplicationList.contains(jobApplication)) {
                    Long id = jobApplication.getId();
                    jobApplicationRepository.deleteById(id);
                    LOGGER.info("Job application with id = {} has been deleted", id);
                }
            });
        }
    }

    private void updateSkillList(VacancyDto dto, Vacancy vacancyResult) {
        Long vacancyId = dto.getId();
        List<Long> skillIdList = dto.getSkillIdList();
        if (!CollectionUtils.isEmpty(skillIdList)) {
            List<Skill> skillList = skillRepository.findAllById(skillIdList);
            Set<Skill> skillsFromUI = new HashSet<>(skillList);
            Vacancy vacancyFromDB = vacancyRepository.findById(vacancyId).orElseThrow();
            Set<Skill> skillsFromDB = vacancyFromDB.getSkills();
            //удаление Скилов из списка базы данных, которе не перечислены пользователем
            skillsFromDB.removeIf(dbSkill -> {
                if (skillsFromUI.stream().noneMatch(dbSkill::equals)) {
                    Set<Vacancy> dbVacancies = dbSkill.getVacancies();
                    dbVacancies.removeIf(vacancy -> vacancy.getId().equals(vacancyId));
                    return true;
                }
                return false;
            });
            //удаление Скилов из списка пользователя, если такие уже записаны в базу
            skillsFromUI.removeIf(uiSkills ->
                    skillsFromDB.stream().anyMatch(skill -> skill.equals(uiSkills)));
            //установить обновленную Вакансию на все новые вакансии
            skillsFromUI.forEach(skill -> skill.addVacancy(vacancyResult));

            skillsFromDB.addAll(skillsFromUI);
            vacancyResult.setSkills(skillsFromDB);
        }
    }

    @Override
    @Transactional
    public Long save(VacancyDto dto) {
        validate(dto, validator);
        Vacancy vacancy = vacancyConverter.convertToEntity(dto);
        checkDtoForExistence(vacancy);
        if (dto.getPlacementDate() == null) {
            dto.setPlacementDate(Utils.getStringFromDate(LocalDateTime.now()));
        }
        try {
            vacancy = vacancyRepository.save(vacancy);
        } catch (RuntimeException exception) {
            String message = "The vacancy already exists.";
            LOGGER.error(message);
            throw new SuchElementAlreadyExistsException(message);
        }
        LOGGER.info("The skill id={} has been saved in the database", vacancy.getId());
        return vacancy.getId();
    }

    private void checkDtoForExistence(Vacancy vacancy) {
        Long vacancyId = vacancy.getId();
        if (vacancyId != null) {
            Optional<Vacancy> optional = vacancyRepository.findById(vacancyId);
            if (optional.isPresent()) {
                String message = MessageFormat
                        .format("The vacancy with id = {0} already exists.", vacancyId);
                LOGGER.error(message);
                throw new SuchElementAlreadyExistsException(message);
            }
        }
    }

    @Override
    @Transactional
    public List<VacancyDto> findAllBySkill(List<Long> skillIdList, Pageable pageable) {
        if (skillIdList == null || CollectionUtils.isEmpty(skillIdList)) {
            throw new IllegalArgumentException();
        }
        List<Skill> skills = new ArrayList<>();
        skillIdList.forEach(id -> skills.add(skillRepository.findById(id).orElseThrow()));

        Specification<Vacancy> specification = VacancySpecification.vacancyContainsSkill(skills.get(0));
        for (Skill skill : skills) {
            specification = specification.and(VacancySpecification.vacancyContainsSkill(skill));
        }
        List<VacancyDto> result = new ArrayList<>();
        List<Vacancy> vacancies = vacancyRepository.findAll(specification, pageable).getContent();
        vacancies.forEach(entity -> result.add(vacancyConverter.convertToDto(entity)));
        return result;
    }
}
