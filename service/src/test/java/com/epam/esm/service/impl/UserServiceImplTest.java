package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.InvalidDtoException;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = ServiceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void shouldFindAll() {
        List<UserDto> all = userService.findAll(PageRequest.of(0, 10));
        assertEquals(4, all.size());
    }

    @Test
    void shouldFindById() {
        UserDto userDto = userService.findById(1L);
        assertEquals("Kiryl", userDto.getName());
    }

    @Test
    void shouldDeleteById() {
        List<UserDto> before = userService.findAll();
        userService.deleteById(4L);
        List<UserDto> after = userService.findAll();
        assertTrue(before.size() > after.size());
    }

    @Test
    void shouldPartialUpdate() {
        UserDto before = userService.findById(1L);
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        String name = "updated name";
        userDto.setName(name);
        UserDto updated = userService.partialUpdate(userDto);

        assertNotEquals(before.getName(), updated.getName());
        assertEquals(name, userDto.getName());
    }

    @Test
    void shouldThrowInvalidIdException() {
        UserDto userDto = new UserDto();
        assertThrows(InvalidDtoException.class, () -> userService.partialUpdate(userDto));
    }

    @Test
    void shouldCreateNewUser() {
        List<Long> rolesIdList = new ArrayList<>();
        rolesIdList.add(1L);
        rolesIdList.add(2L);
        UserDto dto = new UserDto();
        dto.setId(6L);
        dto.setName("Test");
        dto.setPassword("2nd2");
        dto.setRoleIdList(rolesIdList);
        Long id = userService.save(dto);
        UserDto savedDto = userService.findById(id);
        assertEquals(dto.getName(), savedDto.getName());
        assertNotEquals(dto.getId(), savedDto.getId());
        assertEquals(rolesIdList, savedDto.getRoleIdList());
    }

    @Test
    void shouldThrowInvalidDtoException() {
        UserDto dto = new UserDto();
        dto.setName("Test");
        assertThrows(InvalidDtoException.class, () -> userService.save(dto));
    }
}
