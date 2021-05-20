package com.epam.esm.specification;

import com.epam.esm.entity.Skill;
import com.epam.esm.entity.Vacancy;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class VacancySpecification {

    private VacancySpecification() {
    }

    public static Specification<Vacancy> vacancyContainsSkill(Skill skill) {
        return (root, query, criteriaBuilder) -> {
            Join<Vacancy, Skill> skillJoin = root.join("skills");
            return criteriaBuilder.equal(skillJoin.get("name"), skill.getName());
        };
    }
}
