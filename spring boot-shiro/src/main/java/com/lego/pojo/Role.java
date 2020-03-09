package com.lego.pojo;

import java.util.List;

public class Role {
    private Integer id;
    /**
     * 角色标识程序中判断使用，如"admin"，这个是唯一的
     */
    private String role;
    private String description;
    /**
     * 是否可用，如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    /**
     * 角色-限关系，多对多关系
     */
    private List<Permission> permissions;

    /**
     * 用户-角色关系定义，一个角色对应多个用户
     */
    private List<UserInfo> userInfoInfos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<UserInfo> getUserInfoInfos() {
        return userInfoInfos;
    }

    public void setUserInfoInfos(List<UserInfo> userInfoInfos) {
        this.userInfoInfos = userInfoInfos;
    }

    @Override
    public String toString() {
        return String.format("{\"role\":%s,\"description\":%s}", role, description);
    }
}
