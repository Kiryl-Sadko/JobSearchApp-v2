package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.service.RoleService;
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
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    void shouldFindAll() {
        List<RoleDto> all = roleService.findAll(PageRequest.of(0, 10));
        assertEquals(2, all.size());
    }

    @Test
    void shouldFindById() {
        RoleDto roleDto = roleService.findById(1L);
        assertEquals("ADMIN", roleDto.getName());
    }

    @Test
    void shouldDeleteById() {
        boolean isDeleted = roleService.deleteById(1L);
        assertFalse(isDeleted);
    }

    @Test
    void shouldPartialUpdate() {
        RoleDto before = roleService.findById(1L);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        String name = "updated name";
        roleDto.setName(name);
        RoleDto updated = roleService.update(roleDto);

        assertNotEquals(before.getName(), updated.getName());
        assertEquals(name, roleDto.getName());
    }

    @Test
    void shouldThrowInvalidIdException() {
        RoleDto roleDto = new RoleDto();
        assertThrows(InvalidDtoException.class, () -> roleService.update(roleDto));
    }

    @Test
    void shouldCreateNewUser() {
        RoleDto dto = new RoleDto();
        dto.setId(5L);
        dto.setName("Test");
        Long id = roleService.save(dto);
        RoleDto savedDto = roleService.findById(id);
        assertEquals(dto.getName(), savedDto.getName());
        assertNotEquals(dto.getId(), savedDto.getId());
    }

    @Test
    void shouldThrowInvalidDtoException() {
        RoleDto dto = new RoleDto();
        dto.setName("Test");
        assertThrows(InvalidDtoException.class, () -> roleService.save(dto));
    }
}
