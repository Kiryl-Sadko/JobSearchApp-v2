package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

public class SkillDto extends Dto<SkillDto> {

    private Long id;
    private String name;
    private List<VacancyDto> vacancyDtoList;

    public SkillDto() {
    }

    @Override
    public String toString() {
        return "SkillDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vacancyDtoList=" + vacancyDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SkillDto skillDto = (SkillDto) o;
        return Objects.equals(id, skillDto.id) && Objects.equals(name, skillDto.name) && Objects.equals(vacancyDtoList, skillDto.vacancyDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, vacancyDtoList);
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

    public List<VacancyDto> getVacancyDtoList() {
        return vacancyDtoList;
    }

    public void setVacancyDtoList(List<VacancyDto> vacancyDtoList) {
        this.vacancyDtoList = vacancyDtoList;
    }
}
