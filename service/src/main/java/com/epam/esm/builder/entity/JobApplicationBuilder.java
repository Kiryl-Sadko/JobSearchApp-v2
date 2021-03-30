package com.epam.esm.builder.entity;

import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.User;
import com.epam.esm.entity.Vacancy;

import java.math.BigDecimal;
import java.util.Calendar;

public interface JobApplicationBuilder extends EntityBuilder<JobApplication, JobApplicationBuilder> {

    JobApplicationBuilder setUser(User user);

    JobApplicationBuilder setVacancy(Vacancy vacancy);

    JobApplicationBuilder setResponseDate(Calendar responseDate);

    JobApplicationBuilder setSalary(BigDecimal salary);
}
