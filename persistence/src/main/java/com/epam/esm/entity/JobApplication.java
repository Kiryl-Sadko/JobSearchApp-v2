package com.epam.esm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "job_application")
public class JobApplication implements Entity, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @Column(name = "response_date")
    private Calendar responseDate;

    @Column(name = "salary")
    @NotNull(message = "To can't be null")
    @Min(value = 1, message = "To must be greater than zero")
    private BigDecimal salary;

    public JobApplication() {
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", vacancy=" + vacancy.getPosition() +
                ", responseDate=" + responseDate +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication that = (JobApplication) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(vacancy, that.vacancy) && Objects.equals(responseDate, that.responseDate) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, vacancy, responseDate, salary);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Calendar getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Calendar responseDate) {
        this.responseDate = responseDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
