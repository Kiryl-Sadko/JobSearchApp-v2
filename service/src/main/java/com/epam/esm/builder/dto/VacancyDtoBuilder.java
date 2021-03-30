package com.epam.esm.builder.dto;

import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface VacancyDtoBuilder extends DtoBuilder<VacancyDto, VacancyDtoBuilder> {

    VacancyDtoBuilder setPosition(String position);

    VacancyDtoBuilder setPlacementDate(Calendar placementDate);

    VacancyDtoBuilder setLocation(String location);

    VacancyDtoBuilder setSalary(BigDecimal salary);

    VacancyDtoBuilder setEmployer(String employer);

    VacancyDtoBuilder setSkillDtoList(List<SkillDto> skillDtoList);

    VacancyDtoBuilder setJobApplicationDtoList(List<JobApplicationDto> jobApplicationDtoList);
}
