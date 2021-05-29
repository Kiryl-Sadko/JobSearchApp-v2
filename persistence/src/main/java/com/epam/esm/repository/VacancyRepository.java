package com.epam.esm.repository;

import com.epam.esm.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {

    Page<Vacancy> findByPositionStartsWith(String position, Pageable pageable);

    Vacancy findTopByOrderByIdDesc();

    Optional<Vacancy> findByPosition(String position);
}
