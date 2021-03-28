package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class RoleDto extends RepresentationModel<RoleDto> {

    private Long id;
    private String name;
    private List<UserDto> userDtoList;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userDtoList=" + userDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id) && Objects.equals(name, roleDto.name) && Objects.equals(userDtoList, roleDto.userDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, userDtoList);
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

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    public RoleDto() {
    }
}
