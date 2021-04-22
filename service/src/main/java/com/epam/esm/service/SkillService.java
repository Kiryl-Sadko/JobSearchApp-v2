package com.epam.esm.service;

import com.epam.esm.dto.SkillDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService extends CRUDService<SkillDto> {

    List<SkillDto> findByName(String name, Pageable pageable);

    SkillDto findTheMostWidelyUsedSkillWithHighestSalary();
}
