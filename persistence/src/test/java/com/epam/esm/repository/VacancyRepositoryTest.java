package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import com.epam.esm.specification.VacancySpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class VacancyRepositoryTest {

    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void shouldFindVacancyBySkillOrderByDesc() {
        Skill skill_1 = skillRepository.findById(1L).orElseThrow();
        Skill skill_2 = skillRepository.findById(2L).orElseThrow();
        Sort sort = Sort.by(DESC, "id");

        List<Vacancy> vacancies = new ArrayList<>(vacancyRepository.findAll(
                VacancySpecification.vacancyContainsSkill(skill_1)
                        .and(VacancySpecification.vacancyContainsSkill(skill_2)), sort));

        assertEquals(3, vacancies.size());
        assertEquals(4L, vacancies.iterator().next().getId());
    }

    @Test
    public void shouldFindByPositionAndSortingByPositionAndEmployer() {
        Sort sort = Sort.by(ASC, "position", "employer");
        Page<Vacancy> page = vacancyRepository.findByPositionStartsWith("pos",
                PageRequest.of(0, 5, sort));

        List<Vacancy> vacancies = page.getContent();
        assertEquals(4, vacancies.size());
        assertEquals(1L, vacancies.get(0).getId());
        assertEquals(4L, vacancies.get(1).getId());
        assertEquals(3L, vacancies.get(2).getId());
        assertEquals(2L, vacancies.get(3).getId());
    }
}