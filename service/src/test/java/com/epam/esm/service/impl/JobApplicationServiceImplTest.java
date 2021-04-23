package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.service.JobApplicationService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.VacancyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = ServiceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
public class JobApplicationServiceImplTest {

    @Autowired
    private JobApplicationService service;
    @Autowired
    private UserService userService;
    @Autowired
    private VacancyService vacancyService;

    @Test
    void shouldFindAll() {
        List<JobApplicationDto> all = service.findAll(PageRequest.of(0, 10));
        assertEquals(4, all.size());
    }

    @Test
    void shouldFindById() {
        JobApplicationDto dto = service.findById(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    void shouldDeleteById() {
        boolean isDeleted = service.deleteById(1L);
        assertTrue(isDeleted);
    }

    @Test
    void shouldPartialUpdate() {
        JobApplicationDto before = service.findById(1L);
        JobApplicationDto dto = new JobApplicationDto();
        dto.setId(1L);
        BigDecimal salary = BigDecimal.valueOf(120);
        dto.setSalary(salary);
        JobApplicationDto updated = service.update(dto);

        assertNotEquals(before.getSalary(), updated.getSalary());
        assertEquals(salary, dto.getSalary());
    }

    @Test
    void shouldThrowInvalidIdException() {
        JobApplicationDto dto = new JobApplicationDto();
        assertThrows(InvalidDtoException.class, () -> service.update(dto));
    }

    @Test
    void shouldCreateNewJobApplication() {
        JobApplicationDto dto = new JobApplicationDto();
        dto.setId(10L);
        dto.setSalary(BigDecimal.valueOf(120));
        dto.setUserDto(userService.findById(1L));
        dto.setVacancyDto(vacancyService.findById(2L));
        dto.setResponseDate("2018-08-30T01:12:15");
        Long id = service.save(dto);
        JobApplicationDto savedDto = service.findById(id);
        assertEquals(dto.getSalary(), savedDto.getSalary().setScale(0));
        assertNotEquals(dto.getId(), savedDto.getId());
    }

    @Test
    void shouldThrowInvalidDtoException() {
        JobApplicationDto dto = new JobApplicationDto();
        dto.setSalary(BigDecimal.valueOf(120));
        assertThrows(InvalidDtoException.class, () -> service.save(dto));
    }
}
