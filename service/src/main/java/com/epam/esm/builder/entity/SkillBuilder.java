package com.epam.esm.builder.entity;

import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;

import java.util.List;

public interface SkillBuilder extends EntityBuilder<Skill, SkillBuilder> {

    SkillBuilder setName(String name);

    SkillBuilder setVacancies(List<Vacancy> vacancies);
}
