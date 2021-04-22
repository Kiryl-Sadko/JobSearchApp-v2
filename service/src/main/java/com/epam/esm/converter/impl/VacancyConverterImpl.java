package com.epam.esm.converter.impl;

import com.epam.esm.builder.dto.VacancyDtoBuilder;
import com.epam.esm.builder.entity.VacancyBuilder;
import com.epam.esm.converter.VacancyConverter;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.repository.JobApplicationRepository;
import com.epam.esm.repository.SkillRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.epam.esm.service.Utils.*;

@Component
public class VacancyConverterImpl implements VacancyConverter {

    private final SkillRepository skillRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final VacancyBuilder entityBuilder;
    private final VacancyDtoBuilder dtoBuilder;

    public VacancyConverterImpl(SkillRepository skillRepository,
                                JobApplicationRepository jobApplicationRepository,
                                VacancyBuilder entityBuilder,
                                VacancyDtoBuilder dtoBuilder) {
        this.skillRepository = skillRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.entityBuilder = entityBuilder;
        this.dtoBuilder = dtoBuilder;
    }

    @Override
    public Vacancy convertToEntity(VacancyDto dto) {
        List<Long> skillIdList = dto.getSkillIdList();
        List<Skill> skills = skillRepository.findAllById(skillIdList);
        List<Long> jobApplicationIdList = dto.getJobApplicationIdList();
        List<JobApplication> jobApplications = jobApplicationRepository.findAllById(jobApplicationIdList);

        Calendar calendarFromString = null;
        if (dto.getPlacementDate() != null) {
            calendarFromString = getCalendarFromString(dto.getPlacementDate());
        }
        return entityBuilder.setId(dto.getId())
                .setPosition(dto.getPosition())
                .setEmployer(dto.getEmployer())
                .setLocation(dto.getLocation())
                .setSalary(dto.getSalary())
                .setPlacementDate(calendarFromString)
                .setSkills(skills)
                .setJobApplications(jobApplications)
                .build();
    }

    @Override
    public VacancyDto convertToDto(Vacancy entity) {
        List<Skill> skills = entity.getSkills();
        List<Long> skillIdList = new ArrayList<>();
        skills.forEach(skill -> skillIdList.add(skill.getId()));

        List<JobApplication> jobApplications = entity.getJobApplications();
        List<Long> jobApplicationIdList = new ArrayList<>();
        jobApplications.forEach(jobApplication -> jobApplicationIdList.add(jobApplication.getId()));

        return dtoBuilder.setId(entity.getId())
                .setPosition(entity.getPosition())
                .setLocation(entity.getLocation())
                .setEmployer(entity.getEmployer())
                .setSalary(entity.getSalary())
                .setPlacementDate(getStringFromDate(entity.getPlacementDate()))
                .setSkillIdList(skillIdList)
                .setJobApplicationIdList(jobApplicationIdList)
                .build();
    }
}
