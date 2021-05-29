package com.epam.esm.converter.impl;

import com.epam.esm.builder.dto.SkillDtoBuilder;
import com.epam.esm.builder.entity.SkillBuilder;
import com.epam.esm.converter.SkillConverter;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.repository.VacancyRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SkillConverterImpl implements SkillConverter {

    private final VacancyRepository vacancyRepository;
    private final SkillBuilder entityBuilder;
    private final SkillDtoBuilder dtoBuilder;

    public SkillConverterImpl(VacancyRepository vacancyRepository, SkillBuilder entityBuilder,
                              SkillDtoBuilder dtoBuilder) {
        this.vacancyRepository = vacancyRepository;
        this.entityBuilder = entityBuilder;
        this.dtoBuilder = dtoBuilder;
    }

    @Override
    public Skill convertToEntity(SkillDto dto) {
        Long id = dto.getId();
        String name = dto.getName();
        List<Long> vacancyIdList = dto.getVacancyIdList();
        List<Vacancy> vacancyList = vacancyRepository.findAllById(vacancyIdList);
        Set<Vacancy> vacancies = new HashSet<>(vacancyList);

        Skill skill = entityBuilder
                .setId(id)
                .setName(name)
                .setVacancies(vacancies)
                .build();

        vacancies.forEach(vacancy -> vacancy.addSkill(skill));
        return skill;
    }

    @Override
    public SkillDto convertToDto(Skill skill) {
        Set<Vacancy> vacancies = skill.getVacancies();
        List<Long> vacancyIdList = new ArrayList<>();
        vacancies.forEach(vacancy -> vacancyIdList.add(vacancy.getId()));

        return dtoBuilder
                .setId(skill.getId())
                .setName(skill.getName())
                .setVacancyIdList(vacancyIdList)
                .build();
    }
}
