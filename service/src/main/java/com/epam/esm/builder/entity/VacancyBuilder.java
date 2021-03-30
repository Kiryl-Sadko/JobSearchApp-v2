package com.epam.esm.builder.entity;

import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface VacancyBuilder extends EntityBuilder<Vacancy, VacancyBuilder> {

    VacancyBuilder setPosition(String position);

    VacancyBuilder setEmployer(String employer);

    VacancyBuilder setPlacementDate(Calendar placementDate);

    VacancyBuilder setSalary(BigDecimal salary);

    VacancyBuilder setLocation(String location);

    VacancyBuilder setSkills(List<Skill> skills);

    VacancyBuilder setJobApplications(List<JobApplication> jobApplications);
}
