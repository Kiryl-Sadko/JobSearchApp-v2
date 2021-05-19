package com.epam.esm.converter.impl;

import com.epam.esm.builder.dto.JobApplicationDtoBuilder;
import com.epam.esm.builder.entity.JobApplicationBuilder;
import com.epam.esm.converter.JobApplicationConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.converter.VacancyConverter;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.User;
import com.epam.esm.entity.Vacancy;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.Utils.*;

@Component
public class JobApplicationConverterImpl implements JobApplicationConverter {

    private final JobApplicationBuilder entityBuilder;
    private final JobApplicationDtoBuilder dtoBuilder;
    private final UserConverter userConverter;
    private final VacancyConverter vacancyConverter;

    public JobApplicationConverterImpl(JobApplicationBuilder entityBuilder,
                                       JobApplicationDtoBuilder dtoBuilder,
                                       UserConverter userConverter,
                                       VacancyConverter vacancyConverter) {
        this.entityBuilder = entityBuilder;
        this.dtoBuilder = dtoBuilder;
        this.userConverter = userConverter;
        this.vacancyConverter = vacancyConverter;
    }

    @Override
    public JobApplication convertToEntity(JobApplicationDto dto) {
        User user = userConverter.convertToEntity(dto.getUserDto());
        Vacancy vacancy = vacancyConverter.convertToEntity(dto.getVacancyDto());

        return entityBuilder
                .setId(dto.getId())
                .setSalary(dto.getSalary())
                .setResponseDate(getCalendarFromString(dto.getResponseDate()))
                .setUser(user)
                .setVacancy(vacancy)
                .build();
    }

    @Override
    public JobApplicationDto convertToDto(JobApplication entity) {
        UserDto userDto = userConverter.convertToDto(entity.getUser());
        VacancyDto vacancyDto = vacancyConverter.convertToDto(entity.getVacancy());
        String stringFromDate = null;
        if (entity.getResponseDate() != null) {
            stringFromDate = getStringFromDate(entity.getResponseDate());
        }
        return dtoBuilder
                .setId(entity.getId())
                .setSalary(entity.getSalary())
                .setResponseDate(stringFromDate)
                .setUserDto(userDto)
                .setVacancyDto(vacancyDto)
                .build();
    }
}
