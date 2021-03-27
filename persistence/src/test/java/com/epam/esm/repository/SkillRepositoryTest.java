package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
public class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void shouldFindSkillByPartOfNameDesc() {
        Pageable page = PageRequest.of(0, 5, Sort.Direction.DESC, "name");

        Page<Skill> skills = skillRepository.findByNameStartsWith("sk", page);

        assertEquals(3, skills.getContent().size());
        assertEquals("skill_3", skills.getContent().get(0).getName());
    }

    @Test
    public void shouldFindTheMostWidelyUsedSkillWithHighestSalary() {
        Skill skill = skillRepository.findTheMostWidelyUsedSkillWithHighestSalary().orElseThrow();
        assertEquals(2, skill.getId());
    }

    @Test
    public void shouldSaveSkill() {
        Skill skill = new Skill();
        skill.setName("custom skill");
        skill = skillRepository.save(skill);
        assertEquals(4L, skill.getId());
    }

    @Test
    public void shouldDeleteSkill() {
        skillRepository.deleteById(3L);
        assertEquals(2, skillRepository.findAll().size());
    }

    @Test
    public void shouldReturnPaginatedList() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "name");
        Page<Skill> firstPage = skillRepository.findAll(pageable);
        assertEquals(5, firstPage.getSize());
        assertEquals("skill_1", firstPage.getContent().get(0).getName());
    }
}
