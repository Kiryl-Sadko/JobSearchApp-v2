package com.epam.esm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto extends Dto<UserDto> {

    private static final long serialVersionUID = 1414431181820857380L;

    private Long id;
    @NotNull
    @Size(min = 4, max = 30)
    private String name;
    @NotNull
    @Size(min = 4)
    private String password;
    private List<Long> roleIdList = new ArrayList<>();
    private List<Long> applicationIdList = new ArrayList<>();

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applicationIdList=" + applicationIdList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(password, userDto.password) && Objects.equals(roleIdList, userDto.roleIdList) && Objects.equals(applicationIdList, userDto.applicationIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, password, roleIdList, applicationIdList);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Long> getJobApplicationIdList() {
        return applicationIdList;
    }

    public void setApplicationIdList(List<Long> applicationIdList) {
        this.applicationIdList = applicationIdList;
    }
}
