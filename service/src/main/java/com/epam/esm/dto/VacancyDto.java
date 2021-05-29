package com.epam.esm.dto;

import com.epam.esm.service.Utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VacancyDto extends Dto<VacancyDto> {

    private static final long serialVersionUID = 807019565472588846L;

    private Long id;
    @NotNull
    @Size(min = 3, max = 30)
    private String position;
    @Size(min = 3, max = 30)
    private String employer = "unknown";
    private String placementDate = Utils.getStringFromDate(LocalDateTime.now());
    @Min(value = 0)
    private BigDecimal salary = BigDecimal.valueOf(0);
    @Size(min = 3, max = 20)
    private String location = "unknown";
    private List<Long> skillIdList = new ArrayList<>();
    private List<Long> jobApplicationIdList = new ArrayList<>();

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
                ", skillIdList=" + skillIdList +
                ", jobApplicationIdList=" + jobApplicationIdList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VacancyDto dto = (VacancyDto) o;
        return Objects.equals(id, dto.id) && Objects.equals(position, dto.position) && Objects.equals(employer, dto.employer) && Objects.equals(placementDate, dto.placementDate) && Objects.equals(salary, dto.salary) && Objects.equals(location, dto.location) && Objects.equals(skillIdList, dto.skillIdList) && Objects.equals(jobApplicationIdList, dto.jobApplicationIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, position, employer, placementDate, salary, location, skillIdList, jobApplicationIdList);
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

    public List<Long> getSkillIdList() {
        return skillIdList;
    }

    public void setSkillIdList(List<Long> skillIdList) {
        this.skillIdList = skillIdList;
    }

    public List<Long> getJobApplicationIdList() {
        return jobApplicationIdList;
    }

    public void setJobApplicationIdList(List<Long> jobApplicationIdList) {
        this.jobApplicationIdList = jobApplicationIdList;
    }
}
