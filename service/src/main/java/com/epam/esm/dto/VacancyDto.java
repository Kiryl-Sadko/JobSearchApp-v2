package com.epam.esm.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class VacancyDto extends Dto<VacancyDto> {

    private Long id;
    private String position;
    private String employer;
    private String placementDate;
    private BigDecimal salary;
    private String location;
    private List<SkillDto> skills;
    private List<JobApplicationDto> applicationDtoList;

    public VacancyDto() {
    }

    @Override
    public String toString() {
        return "VacancyDto{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", employer='" + employer + '\'' +
                ", placementDate='" + placementDate + '\'' +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", skills=" + skills +
                ", applicationDtoList=" + applicationDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VacancyDto that = (VacancyDto) o;
        return Objects.equals(id, that.id) && Objects.equals(position, that.position) && Objects.equals(employer, that.employer) && Objects.equals(placementDate, that.placementDate) && Objects.equals(salary, that.salary) && Objects.equals(location, that.location) && Objects.equals(skills, that.skills) && Objects.equals(applicationDtoList, that.applicationDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, position, employer, placementDate, salary, location, skills, applicationDtoList);
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

    public String getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(String placementDate) {
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

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<JobApplicationDto> getApplicationDtoList() {
        return applicationDtoList;
    }

    public void setApplicationDtoList(List<JobApplicationDto> applicationDtoList) {
        this.applicationDtoList = applicationDtoList;
    }
}
