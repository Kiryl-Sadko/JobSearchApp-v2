package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.JobApplicationDtoBuilder;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;

public class JobApplicationDtoBuilderImpl implements JobApplicationDtoBuilder {

    private JobApplicationDto jobApplicationDto = new JobApplicationDto();

    @Override
    public JobApplicationDto build() {
        JobApplicationDto result = new JobApplicationDto();
        result.setId(jobApplicationDto.getId());
        result.setVacancyDto(jobApplicationDto.getVacancyDto());
        result.setUserDto(jobApplicationDto.getUserDto());
        result.setSalary(jobApplicationDto.getSalary());
        result.setResponseDate(jobApplicationDto.getResponseDate());
        this.reset();
        return result;
    }

    @Override
    public JobApplicationDtoBuilder reset() {
        jobApplicationDto = new JobApplicationDto();
        return this;
    }

    @Override
    public JobApplicationDtoBuilder setId(Long id) {
        jobApplicationDto.setId(id);
        return this;
    }

    @Override
    public JobApplicationDtoBuilder setUserDto(UserDto userDto) {
        jobApplicationDto.setUserDto(userDto);
        return this;
    }

    @Override
    public JobApplicationDtoBuilder setVacancyDto(VacancyDto vacancyDto) {
        jobApplicationDto.setVacancyDto(vacancyDto);
        return this;
    }

    @Override
    public JobApplicationDtoBuilder setResponseDate(String responseDate) {
        jobApplicationDto.setResponseDate(responseDate);
        return this;
    }

    @Override
    public JobApplicationDtoBuilder setSalary(BigDecimal salary) {
        jobApplicationDto.setSalary(salary);
        return this;
    }
}
