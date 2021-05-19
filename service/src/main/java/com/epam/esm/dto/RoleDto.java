package com.epam.esm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDto extends Dto<RoleDto> {

    private static final long serialVersionUID = 6746907035784755098L;

    private Long id;
    @NotNull
    @Size(min = 4, max = 30)
    private String name;
    private List<Long> userIdList = new ArrayList<>();

    public RoleDto() {
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userIdList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id) && Objects.equals(name, roleDto.name) && Objects.equals(userIdList, roleDto.userIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, userIdList);
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

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }
}
