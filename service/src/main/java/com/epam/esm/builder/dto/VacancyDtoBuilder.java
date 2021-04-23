package com.epam.esm.builder.dto;

import com.epam.esm.dto.VacancyDto;

import java.math.BigDecimal;
import java.util.List;

public interface VacancyDtoBuilder extends DtoBuilder<VacancyDto, VacancyDtoBuilder> {

    VacancyDtoBuilder setPosition(String position);

    VacancyDtoBuilder setPlacementDate(String placementDate);

    VacancyDtoBuilder setLocation(String location);

    VacancyDtoBuilder setSalary(BigDecimal salary);

    VacancyDtoBuilder setEmployer(String employer);

    VacancyDtoBuilder setSkillIdList(List<Long> skillIdList);

    VacancyDtoBuilder setJobApplicationIdList(List<Long> jobApplicationIdList);
}
