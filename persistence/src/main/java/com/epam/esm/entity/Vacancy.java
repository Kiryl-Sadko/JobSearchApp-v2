package com.epam.esm.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "vacancy")
public class Vacancy implements Entity {

    private static final long serialVersionUID = -2585319354824992287L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "employer")
    private String employer;

    @Column(name = "placement_date")
    private LocalDateTime placementDate;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "location")
    private String location;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "vacancy_skill",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "vacancy")
    private Set<JobApplication> jobApplications;

    public Vacancy() {
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", employer='" + employer + '\'' +
                ", placementDate=" + placementDate +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public LocalDateTime getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDateTime placementDate) {
        this.placementDate = placementDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Set<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void addJobApplication(JobApplication jobApplication) {
        jobApplications.add(jobApplication);
    }
}
