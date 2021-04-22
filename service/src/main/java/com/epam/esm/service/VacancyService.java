package com.epam.esm.service;

import com.epam.esm.dto.SkillDto;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.entity.Skill;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService extends CRUDService<VacancyDto> {

    List<Skill> findOrCreateSkill(List<Skill> skillList);

    List<VacancyDto> findVacancyBySkill(List<SkillDto> skillDtoList, Pageable pageable);

}
