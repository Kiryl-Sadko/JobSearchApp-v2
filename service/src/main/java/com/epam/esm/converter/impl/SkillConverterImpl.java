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
import java.util.List;

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
        List<Long> vacancyIdList = dto.getVacancyIdList();
        List<Vacancy> vacancies = vacancyRepository.findAllById(vacancyIdList);

        return entityBuilder.setId(dto.getId())
                .setName(dto.getName())
                .setVacancies(vacancies)
                .build();
    }

    @Override
    public SkillDto convertToDto(Skill entity) {
        List<Vacancy> vacancies = entity.getVacancies();
        List<Long> vacancyIdList = new ArrayList<>();
        vacancies.forEach(vacancy -> vacancyIdList.add(vacancy.getId()));

        return dtoBuilder.setId(entity.getId())
                .setName(entity.getName())
                .setVacancyIdList(vacancyIdList)
                .build();
    }
}
