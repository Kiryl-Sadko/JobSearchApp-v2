package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.JobApplication;
import com.epam.esm.entity.User;
import com.epam.esm.entity.Vacancy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
public class JobApplicationRepositoryTest {

    @Autowired
    private JobApplicationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Test
    public void shouldSaveJobApplication() {
        User user = userRepository.findTopByOrderByIdDesc();
        Vacancy vacancy = vacancyRepository.findTopByOrderByIdDesc();

        JobApplication jobApplication = new JobApplication();
        jobApplication.setSalary(BigDecimal.valueOf(100));
        jobApplication.setUser(user);
        jobApplication.setVacancy(vacancy);
        jobApplication.setResponseDate(LocalDateTime.now());

        List<JobApplication> before = repository.findAll();
        repository.save(jobApplication);

        List<JobApplication> after = repository.findAll();
        int beforeSize = before.size();
        int afterSize = after.size();
        assertEquals(afterSize, ++beforeSize);
    }

    @Test
    public void shouldFindAllJobApplications() {
        Page<JobApplication> all = repository.findAll(PageRequest.of(0, 5));
        assertEquals(4, all.getContent().size());
    }

    @Test
    public void shouldFindJobApplicationById() {
        JobApplication jobApplication = repository.findById(1L).orElseThrow();
        assertEquals(1L, jobApplication.getVacancy().getId());
    }

    @Test
    public void shouldFindJobApplicationsByUserId() {
        Page<JobApplication> byUserId = repository.findByUserId(1L, PageRequest.of(0, 5));
        List<JobApplication> applicationList = byUserId.getContent();
        assertEquals(4, applicationList.size());
    }

    @Test
    public void shouldDeleteById() {
        repository.deleteById(1L);
        int size = repository.findAll().size();
        assertEquals(3, size);
    }
}
