package com.epam.esm.builder.entity.impl;

import com.epam.esm.builder.entity.JobApplicationBuilder;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.User;
import com.epam.esm.entity.Vacancy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class JobApplicationBuilderImpl implements JobApplicationBuilder {

    private JobApplication jobApplication = new JobApplication();

    @Override
    public JobApplication build() {
        JobApplication result = new JobApplication();
        result.setId(jobApplication.getId());
        result.setVacancy(jobApplication.getVacancy());
        result.setUser(jobApplication.getUser());
        result.setSalary(jobApplication.getSalary());
        result.setResponseDate(jobApplication.getResponseDate());
        this.reset();
        return result;
    }

    @Override
    public JobApplicationBuilder reset() {
        jobApplication = new JobApplication();
        return this;
    }

    @Override
    public JobApplicationBuilder setId(Long id) {
        jobApplication.setId(id);
        return this;
    }

    @Override
    public JobApplicationBuilder setUser(User user) {
        jobApplication.setUser(user);
        return this;
    }

    @Override
    public JobApplicationBuilder setVacancy(Vacancy vacancy) {
        jobApplication.setVacancy(vacancy);
        return this;
    }

    @Override
    public JobApplicationBuilder setResponseDate(LocalDateTime responseDate) {
        jobApplication.setResponseDate(responseDate);
        return this;
    }

    @Override
    public JobApplicationBuilder setSalary(BigDecimal salary) {
        jobApplication.setSalary(salary);
        return this;
    }
}
