package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindById() {
        Optional<User> optionalUser = userRepository.findById(1L);
        User user = optionalUser.orElseThrow();

        assertTrue(user.getName().equalsIgnoreCase("kiryl"));
    }

    @Test
    public void shouldFindByPartOfName() {
        Page<User> page = userRepository.findUserByNameStartsWith("Kir",
                PageRequest.of(0, 5, ASC, "name"));
        List<User> users = page.getContent();
        User user = users.get(0);

        assertTrue(user.getName().equalsIgnoreCase("kiryl"));
        assertEquals(users.size(), 1);
    }

    @Test
    public void shouldFindSkillsByPartOfName() {
        Page<User> page = userRepository.findUserByNameStartsWith("",
                PageRequest.of(0, 5, ASC, "name"));
        List<User> users = page.getContent();
        assertTrue(users.size() > 1);
    }

    @Test
    public void shouldFindUsersByRoles() {
        Optional<Role> optionalRole = roleRepository.findById(1L);
        Role role = optionalRole.orElseThrow();
        Page<User> page = userRepository.findUsersByRolesIn(PageRequest.of(0, 5, ASC, "name"), role);

        List<User> usersByRoles = page.getContent();
        User user = usersByRoles.get(0);

        assertTrue(user.getName().equalsIgnoreCase("Kiryl"));
        assertEquals(usersByRoles.size(), 1);
    }

    @Test
    public void shouldSaveUser() {
        User user = new User();
        user.setName("custom user");
        user.setPassword("123");

        user = userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void shouldThrowException() {
        EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class,
                () -> userRepository.deleteById(4L));
        assertEquals("No class com.epam.esm.entity.User entity with id 4 exists!", exception.getMessage());
    }

    @Test
    public void shouldDeleteUser() {
        int initialSize = userRepository.findAll().size();
        userRepository.deleteById(3L);
        int finalSize = userRepository.findAll().size();
        assertTrue(finalSize < initialSize);
    }
}