package com.epam.esm.service;

import com.epam.esm.dto.JobApplicationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobApplicationService extends CRUDService<JobApplicationDto> {

    List<JobApplicationDto> findByUserId(Long userId, Pageable pageable);

    JobApplicationDto create(Long userId, Long vacancyId);
}
