package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

public class UserDto extends Dto<UserDto> {

    private Long id;
    private String name;
    private List<RoleDto> roleDtoList;
    private List<JobApplicationDto> applicationDtoList;

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleDtoList=" + roleDtoList +
                ", applicationDtoList=" + applicationDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(roleDtoList, userDto.roleDtoList) && Objects.equals(applicationDtoList, userDto.applicationDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, roleDtoList, applicationDtoList);
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

    public List<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(List<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }

    public List<JobApplicationDto> getApplicationDtoList() {
        return applicationDtoList;
    }

    public void setApplicationDtoList(List<JobApplicationDto> applicationDtoList) {
        this.applicationDtoList = applicationDtoList;
    }
}
