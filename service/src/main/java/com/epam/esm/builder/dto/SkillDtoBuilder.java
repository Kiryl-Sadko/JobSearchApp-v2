package com.epam.esm.builder.dto;

import com.epam.esm.dto.SkillDto;

import java.util.List;

public interface SkillDtoBuilder extends DtoBuilder<SkillDto, SkillDtoBuilder> {

    SkillDtoBuilder setName(String name);

    SkillDtoBuilder setVacancyIdList(List<Long> vacancyIdList);
}
