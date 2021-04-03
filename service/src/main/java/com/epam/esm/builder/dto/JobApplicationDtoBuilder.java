package com.epam.esm.builder.dto;

import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;

public interface JobApplicationDtoBuilder extends DtoBuilder<JobApplicationDto, JobApplicationDtoBuilder> {

    JobApplicationDtoBuilder setUserDto(UserDto userDto);

    JobApplicationDtoBuilder setVacancyDto(VacancyDto vacancyDto);

    JobApplicationDtoBuilder setResponseDate(String responseDate);

    JobApplicationDtoBuilder setSalary(BigDecimal salary);
}
