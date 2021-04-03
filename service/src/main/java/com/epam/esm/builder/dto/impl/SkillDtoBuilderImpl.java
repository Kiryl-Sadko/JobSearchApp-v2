package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.SkillDtoBuilder;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;

import java.util.List;

public class SkillDtoBuilderImpl implements SkillDtoBuilder {

    private SkillDto skillDto = new SkillDto();

    @Override
    public SkillDto build() {
        SkillDto result = new SkillDto();
        result.setId(skillDto.getId());
        result.setName(skillDto.getName());
        result.setVacancyDtoList(skillDto.getVacancyDtoList());
        this.reset();
        return result;
    }

    @Override
    public SkillDtoBuilder reset() {
        skillDto = new SkillDto();
        return this;
    }

    @Override
    public SkillDtoBuilder setId(Long id) {
        skillDto.setId(id);
        return this;
    }

    @Override
    public SkillDtoBuilder setName(String name) {
        skillDto.setName(name);
        return this;
    }

    @Override
    public SkillDtoBuilder setVacancyDtoList(List<VacancyDto> vacancyDtoList) {
        skillDto.setVacancyDtoList(vacancyDtoList);
        return this;
    }
}
