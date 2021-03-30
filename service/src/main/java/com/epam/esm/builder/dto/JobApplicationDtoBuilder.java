package com.epam.esm.builder.dto;

import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;
import java.util.Calendar;

public interface JobApplicationDtoBuilder extends DtoBuilder<JobApplicationDto, JobApplicationDtoBuilder> {

    JobApplicationDtoBuilder setUserDto(UserDto userDto);

    JobApplicationDtoBuilder setVacancyDto(VacancyDto vacancyDto);

    JobApplicationDtoBuilder setResponseDate(Calendar responseDate);

    JobApplicationDtoBuilder setSalary(BigDecimal salary);
}
