package com.epam.esm.builder.dto;

import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;

import java.util.List;

public interface SkillDtoBuilder extends DtoBuilder<SkillDto, SkillDtoBuilder> {

    SkillDtoBuilder setName(String name);

    SkillDtoBuilder setVacancyDtoList(List<VacancyDto> vacancyDtoList);
}
