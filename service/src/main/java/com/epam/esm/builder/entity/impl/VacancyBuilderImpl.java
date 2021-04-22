package com.epam.esm.builder.entity.impl;

import com.epam.esm.builder.entity.VacancyBuilder;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Component
public class VacancyBuilderImpl implements VacancyBuilder {

    private Vacancy vacancy = new Vacancy();

    @Override
    public Vacancy build() {
        Vacancy result = new Vacancy();
        result.setId(vacancy.getId());
        result.setPosition(vacancy.getPosition());
        result.setEmployer(vacancy.getEmployer());
        result.setLocation(vacancy.getLocation());
        result.setSalary(vacancy.getSalary());
        result.setSkills(vacancy.getSkills());
        result.setPlacementDate(vacancy.getPlacementDate());
        result.setJobApplications(vacancy.getJobApplications());
        result.setDeleted(vacancy.isDeleted());
        this.reset();
        return result;
    }

    @Override
    public VacancyBuilder reset() {
        vacancy = new Vacancy();
        return this;
    }

    @Override
    public VacancyBuilder setId(Long id) {
        vacancy.setId(id);
        return this;
    }

    @Override
    public VacancyBuilder setPosition(String position) {
        vacancy.setPosition(position);
        return this;
    }

    @Override
    public VacancyBuilder setEmployer(String employer) {
        vacancy.setEmployer(employer);
        return this;
    }

    @Override
    public VacancyBuilder setPlacementDate(Calendar placementDate) {
        vacancy.setPlacementDate(placementDate);
        return this;
    }

    @Override
    public VacancyBuilder setSalary(BigDecimal salary) {
        vacancy.setSalary(salary);
        return this;
    }

    @Override
    public VacancyBuilder setLocation(String location) {
        vacancy.setLocation(location);
        return this;
    }

    @Override
    public VacancyBuilder setSkills(List<Skill> skills) {
        vacancy.setSkills(skills);
        return this;
    }

    @Override
    public VacancyBuilder setJobApplications(List<JobApplication> jobApplications) {
        vacancy.setJobApplications(jobApplications);
        return this;
    }
}
