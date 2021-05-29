package com.epam.esm.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class JobApplicationDto extends Dto<JobApplicationDto> {

    private static final long serialVersionUID = 4542613022872771213L;

    private Long id;
    @NotNull
    private UserDto userDto;
    @NotNull
    private VacancyDto vacancyDto;
    @NotNull
    private String responseDate;
    @Min(value = 0)
    private BigDecimal salary;

    public JobApplicationDto() {
    }

    @Override
    public String toString() {
        return "JobApplicationDto{" +
                "id=" + id +
                ", responseDate='" + responseDate + '\'' +
                ", salary=" + salary +
                ", userDto=" + userDto +
                ", vacancyDto=" + vacancyDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobApplicationDto that = (JobApplicationDto) o;
        return Objects.equals(id, that.id) && Objects.equals(userDto, that.userDto) && Objects.equals(vacancyDto, that.vacancyDto) && Objects.equals(responseDate, that.responseDate) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userDto, vacancyDto, responseDate, salary);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public VacancyDto getVacancyDto() {
        return vacancyDto;
    }

    public void setVacancyDto(VacancyDto vacancyDto) {
        this.vacancyDto = vacancyDto;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
