package com.epam.esm.repository;

import com.epam.esm.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Page<Skill> findByNameStartsWith(String name, Pageable pageable);

    Optional<Skill> findByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT skill.*, COUNT(skill.name) AS qty from job_application\n" +
                    "inner join user u on u.id = job_application.user_id\n" +
                    "inner join vacancy_skill on (job_application.vacancy_id=vacancy_skill.vacancy_id)\n" +
                    "inner join skill on (skill.id=vacancy_skill.skill_id) \n" +
                    "where job_application.user_id = \n" +
                    "(select job_application.user_id from job_application group by \n" +
                    "job_application.user_id order by sum(job_application.salary) \n" +
                    "desc limit 1) group by skill.name\n" +
                    "order by qty desc limit 1")
    Optional<Skill> findTheMostWidelyUsedSkillWithHighestSalary();

    Skill findTopByOrderByIdDesc();
}
