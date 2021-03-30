package com.epam.esm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "employer")
    private String employer;

    @Column(name = "placement_date")
    private Calendar placementDate;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "location")
    private String location;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "vacancy_skill",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER)
    private List<JobApplication> jobApplications;

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
                ", skills=" + skills +
                ", applications=" + jobApplications +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return isDeleted == vacancy.isDeleted && Objects.equals(id, vacancy.id) && Objects.equals(position, vacancy.position) && Objects.equals(employer, vacancy.employer) && Objects.equals(placementDate, vacancy.placementDate) && Objects.equals(salary, vacancy.salary) && Objects.equals(location, vacancy.location) && Objects.equals(skills, vacancy.skills) && Objects.equals(jobApplications, vacancy.jobApplications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, employer, placementDate, salary, location, isDeleted, skills, jobApplications);
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

    public Calendar getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Calendar placementDate) {
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }
}
