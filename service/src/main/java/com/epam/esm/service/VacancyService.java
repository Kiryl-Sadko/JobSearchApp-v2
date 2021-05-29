package com.epam.esm.service;

import com.epam.esm.dto.VacancyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService extends CRUDService<VacancyDto> {

    List<VacancyDto> findAllBySkill(List<Long> skillIdList, Pageable pageable);

}
