package com.epam.esm.builder.entity;

import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public interface VacancyBuilder extends EntityBuilder<Vacancy, VacancyBuilder> {

    VacancyBuilder setPosition(String position);

    VacancyBuilder setEmployer(String employer);

    VacancyBuilder setPlacementDate(LocalDateTime placementDate);

    VacancyBuilder setSalary(BigDecimal salary);

    VacancyBuilder setLocation(String location);

    VacancyBuilder setSkills(Set<Skill> skills);

    VacancyBuilder setJobApplications(Set<JobApplication> jobApplications);
}
