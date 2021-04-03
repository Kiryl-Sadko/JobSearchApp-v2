package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.VacancyDtoBuilder;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;
import java.util.List;

public class VacancyDtoBuilderImpl implements VacancyDtoBuilder {

    private VacancyDto vacancyDto = new VacancyDto();

    @Override
    public VacancyDto build() {
        VacancyDto result = new VacancyDto();
        result.setId(vacancyDto.getId());
        result.setPosition(vacancyDto.getPosition());
        result.setSalary(vacancyDto.getSalary());
        result.setEmployer(vacancyDto.getEmployer());
        result.setLocation(vacancyDto.getLocation());
        result.setPlacementDate(vacancyDto.getPlacementDate());
        result.setSkillDtoList(vacancyDto.getSkillDtoList());
        result.setApplicationDtoList(vacancyDto.getApplicationDtoList());
        this.reset();
        return result;
    }

    @Override
    public VacancyDtoBuilder reset() {
        vacancyDto = new VacancyDto();
        return this;
    }

    @Override
    public VacancyDtoBuilder setId(Long id) {
        vacancyDto.setId(id);
        return this;
    }

    @Override
    public VacancyDtoBuilder setPosition(String position) {
        vacancyDto.setPosition(position);
        return this;
    }

    @Override
    public VacancyDtoBuilder setPlacementDate(String placementDate) {
        vacancyDto.setPlacementDate(placementDate);
        return this;
    }

    @Override
    public VacancyDtoBuilder setLocation(String location) {
        vacancyDto.setLocation(location);
        return this;
    }

    @Override
    public VacancyDtoBuilder setSalary(BigDecimal salary) {
        vacancyDto.setSalary(salary);
        return this;
    }

    @Override
    public VacancyDtoBuilder setEmployer(String employer) {
        vacancyDto.setEmployer(employer);
        return this;
    }

    @Override
    public VacancyDtoBuilder setSkillDtoList(List<SkillDto> skillDtoList) {
        vacancyDto.setSkillDtoList(skillDtoList);
        return this;
    }

    @Override
    public VacancyDtoBuilder setJobApplicationDtoList(List<JobApplicationDto> jobApplicationDtoList) {
        vacancyDto.setApplicationDtoList(jobApplicationDtoList);
        return this;
    }
}
