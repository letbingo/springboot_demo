package com.lego.pojo;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private Integer uid;
    /**
     * 帐号
     */
    private String username;
    /**
     * 昵称或者真实姓名，不同系统不同定义
     */
    private String name;
    private String password;
    /**
     * 昵称或者真实姓名，不同系统不同定义
     */
    private String salt;
    /**
     * 用户状态。0:待验证的用户 ，1:正常状态，2：用户被锁定.
     */
    private byte state;
    /**
     * 用户角色列表
     */
    private List<Role> roleList;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    @Override
    public String toString() {
        return String.format("{\"uid\":%s,\"username\":%s,\"password\":%s,\"salt\":%s}",
                uid, username, password, salt);
    }
}
