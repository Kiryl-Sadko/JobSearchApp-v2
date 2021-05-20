package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.service.SkillService;
import com.epam.esm.service.VacancyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = ServiceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class VacancyServiceImplTest {

    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private SkillService skillService;

    @Test
    void shouldFindAll() {
        List<VacancyDto> all = vacancyService.findAll(PageRequest.of(0, 10));
        assertEquals(5, all.size());
    }

    @Test
    void shouldFindById() {
        VacancyDto vacancyDto = vacancyService.findById(1L);
        assertEquals("position_1", vacancyDto.getPosition());
    }

    @Test
    void shouldDeleteById() {
        List<VacancyDto> before = vacancyService.findAll();
        vacancyService.deleteById(5L);
        List<VacancyDto> after = vacancyService.findAll();
        assertTrue(before.size() > after.size());
    }

    @Test
    void shouldPartialUpdate() {
        VacancyDto before = vacancyService.findById(1L);
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setId(1L);
        String position = "updated position";
        vacancyDto.setPosition(position);
        VacancyDto updated = vacancyService.partialUpdate(vacancyDto);

        assertNotEquals(before.getPosition(), updated.getPosition());
        assertEquals(position, vacancyDto.getPosition());
    }

    @Test
    void shouldThrowInvalidIdException() {
        VacancyDto vacancyDto = new VacancyDto();
        assertThrows(InvalidDtoException.class, () -> vacancyService.partialUpdate(vacancyDto));
    }

    @Test
    void shouldCreateNewSkill() {
        VacancyDto dto = new VacancyDto();
        dto.setId(100L);
        dto.setPosition("Test");
        dto.setPlacementDate("2018-08-30T01:12:15");
        dto.setLocation("location");
        dto.setSalary(new BigDecimal(100));
        dto.setEmployer("Emp");
        Long id = vacancyService.save(dto);
        VacancyDto savedDto = vacancyService.findById(id);
        assertEquals(dto.getPosition(), savedDto.getPosition());
        assertNotEquals(dto.getId(), savedDto.getId());
    }

    @Test
    void findVacancyBySkill() {
        List<Long> emptyList = new ArrayList<>();
        PageRequest pageable = PageRequest.of(0, 10);
        assertThrows(IllegalArgumentException.class, () -> vacancyService.findAllBySkill(emptyList, pageable));

        List<Long> filledList = new ArrayList<>();
        filledList.add(1L);
        filledList.add(2L);
        List<VacancyDto> vacancyBySkill = vacancyService.findAllBySkill(filledList, pageable);
        assertEquals(3, vacancyBySkill.size());
    }
}