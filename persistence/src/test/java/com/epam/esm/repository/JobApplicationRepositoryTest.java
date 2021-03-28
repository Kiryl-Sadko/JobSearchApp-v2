package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.JobApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = PersistenceConfig.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
public class JobApplicationRepositoryTest {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Test
    public void shouldFindAllApplications() {
        Page<JobApplication> all = jobApplicationRepository.findAll(PageRequest.of(0, 5));
        Assertions.assertEquals(4, all.getContent().size());
    }

    @Test
    public void shouldFindApplicationById() {
        JobApplication jobApplication = jobApplicationRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(1L, jobApplication.getVacancy().getId());
    }
}
