package com.epam.esm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillDto extends Dto<SkillDto> {

    @NotNull
    private Long id;
    @Size(min = 4, max = 10)
    private String name;
    @NotNull
    private List<Long> vacancyIdList = new ArrayList<>();

    public SkillDto() {
    }

    @Override
    public String toString() {
        return "SkillDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vacancyIdList=" + vacancyIdList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SkillDto skillDto = (SkillDto) o;
        return Objects.equals(id, skillDto.id)
                && Objects.equals(name, skillDto.name)
                && Objects.equals(vacancyIdList, skillDto.vacancyIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, vacancyIdList);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getVacancyIdList() {
        return vacancyIdList;
    }

    public void setVacancyIdList(List<Long> vacancyIdList) {
        this.vacancyIdList = vacancyIdList;
    }
}
