package com.epam.esm.builder.entity.impl;

import com.epam.esm.builder.entity.SkillBuilder;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;

import java.util.List;

public class SkillBuilderImpl implements SkillBuilder {

    private Skill skill = new Skill();

    @Override
    public Skill build() {
        Skill result = new Skill();
        result.setId(skill.getId());
        result.setName(skill.getName());
        result.setVacancies(skill.getVacancies());
        this.reset();
        return result;
    }

    @Override
    public SkillBuilder reset() {
        skill = new Skill();
        return this;
    }

    @Override
    public SkillBuilder setId(Long id) {
        skill.setId(id);
        return this;
    }

    @Override
    public SkillBuilder setName(String name) {
        skill.setName(name);
        return this;
    }

    @Override
    public SkillBuilder setVacancies(List<Vacancy> vacancies) {
        skill.setVacancies(vacancies);
        return this;
    }
}
