package com.epam.esm.repository;

import com.epam.esm.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByUserId(Long id, Pageable pageable);

    List<JobApplication> findByVacancyId(Long id);

    JobApplication findTopByOrderByIdDesc();
}
