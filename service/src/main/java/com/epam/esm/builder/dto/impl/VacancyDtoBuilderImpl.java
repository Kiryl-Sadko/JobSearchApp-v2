package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.VacancyDtoBuilder;
import com.epam.esm.dto.VacancyDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
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
        result.setSkillIdList(vacancyDto.getSkillIdList());
        result.setJobApplicationIdList(vacancyDto.getJobApplicationIdList());
        this.reset();
        return result;
    }

    @Override
    public VacancyDtoBuilder reset() {
        vacancyDto = new VacancyDto();
        return this;
    }

    @Override
    public VacancyDtoBuilder setId(java.lang.Long id) {
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
    public VacancyDtoBuilder setSkillIdList(List<Long> skillIdList) {
        vacancyDto.setSkillIdList(skillIdList);
        return this;
    }

    @Override
    public VacancyDtoBuilder setJobApplicationIdList(List<Long> jobApplicationIdList) {
        vacancyDto.setJobApplicationIdList(jobApplicationIdList);
        return this;
    }
}
