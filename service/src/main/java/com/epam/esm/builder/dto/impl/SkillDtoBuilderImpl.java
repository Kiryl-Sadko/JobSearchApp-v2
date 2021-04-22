package com.epam.esm.builder.dto.impl;

import com.epam.esm.builder.dto.SkillDtoBuilder;
import com.epam.esm.dto.SkillDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkillDtoBuilderImpl implements SkillDtoBuilder {

    private SkillDto skillDto = new SkillDto();

    @Override
    public SkillDto build() {
        SkillDto result = new SkillDto();
        result.setId(skillDto.getId());
        result.setName(skillDto.getName());
        result.setVacancyIdList(skillDto.getVacancyIdList());
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
    public SkillDtoBuilder setVacancyIdList(List<Long> vacancyIdList) {
        skillDto.setVacancyIdList(vacancyIdList);
        return this;
    }
}
