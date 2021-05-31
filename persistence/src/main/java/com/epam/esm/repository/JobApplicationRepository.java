package com.epam.esm.repository;

import com.epam.esm.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUserId(Long id);

    List<JobApplication> findByVacancyId(Long id);

    JobApplication findTopByOrderByIdDesc();
}
