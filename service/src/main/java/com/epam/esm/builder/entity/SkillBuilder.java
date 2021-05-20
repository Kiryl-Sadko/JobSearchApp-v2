package com.epam.esm.builder.entity;

import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;

import java.util.Set;

public interface SkillBuilder extends EntityBuilder<Skill, SkillBuilder> {

    SkillBuilder setName(String name);

    SkillBuilder setVacancies(Set<Vacancy> vacancies);
}
