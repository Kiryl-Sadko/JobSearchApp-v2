package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.SkillDto;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = ServiceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class SkillServiceImplTest {

    @Autowired
    private SkillService skillService;

    @Test
    void shouldFindAll() {
        List<SkillDto> all = skillService.findAll(PageRequest.of(0, 10));
        assertEquals(3, all.size());
    }

    @Test
    void shouldFindById() {
        SkillDto skillDto = skillService.findById(1L);
        assertEquals("skill_1", skillDto.getName());
    }

    @Test
    void shouldDeleteById() {
        boolean isDeleted = skillService.deleteById(1L);
        assertFalse(isDeleted);
        isDeleted = skillService.deleteById(3L);
        assertTrue(isDeleted);
    }

    @Test
    void shouldPartialUpdate() {
        SkillDto before = skillService.findById(1L);
        SkillDto skillDto = new SkillDto();
        skillDto.setId(1L);
        String name = "updated name";
        skillDto.setName(name);
        SkillDto updated = skillService.update(skillDto);

        assertNotEquals(before.getName(), updated.getName());
        assertEquals(name, skillDto.getName());
    }

    @Test
    void shouldThrowInvalidIdException() {
        SkillDto skillDto = new SkillDto();
        assertThrows(InvalidDtoException.class, () -> skillService.update(skillDto));
    }

    @Test
    void shouldCreateNewSkill() {
        SkillDto dto = new SkillDto();
        dto.setId(5L);
        dto.setName("Test");
        Long id = skillService.save(dto);
        SkillDto savedDto = skillService.findById(id);
        assertEquals(dto.getName(), savedDto.getName());
        assertNotEquals(dto.getId(), savedDto.getId());
    }

    @Test
    void shouldThrowInvalidDtoException() {
        SkillDto dto = new SkillDto();
        dto.setName("Test");
        assertThrows(InvalidDtoException.class, () -> skillService.save(dto));
    }

    @Test
    void shouldFindByName() {
        List<SkillDto> skill = skillService.findByName("skill", PageRequest.of(0, 5));
        assertEquals(3, skill.size());
    }

    @Test
    void shouldFindTheMostWidelyUsedSkillWithHighestSalary() {
        SkillDto skillDto = skillService.findTheMostWidelyUsedSkillWithHighestSalary();
        assertEquals(2, skillDto.getId());
    }
}