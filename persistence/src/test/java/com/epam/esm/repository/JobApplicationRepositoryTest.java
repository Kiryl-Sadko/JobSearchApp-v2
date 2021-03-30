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

import java.util.List;

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
    public void shouldFindAllJobApplications() {
        Page<JobApplication> all = jobApplicationRepository.findAll(PageRequest.of(0, 5));
        Assertions.assertEquals(4, all.getContent().size());
    }

    @Test
    public void shouldFindJobApplicationById() {
        JobApplication jobApplication = jobApplicationRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(1L, jobApplication.getVacancy().getId());
    }

    @Test
    public void shouldFindJobApplicationsByUserId() {
        Page<JobApplication> byUserId = jobApplicationRepository.findByUserId(1L, PageRequest.of(0, 5));
        List<JobApplication> applicationList = byUserId.getContent();
        Assertions.assertEquals(4, applicationList.size());
    }
}
