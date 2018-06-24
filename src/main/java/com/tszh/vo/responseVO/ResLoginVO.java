package com.tszh.vo.responseVO;

import com.tszh.entity.Role;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
public class ResLoginVO {

    private int id;

    private String email;

    private String username;

    private Byte gender;

    private String roleName;

    private String lastLoginDate;

    public ResLoginVO(){

    }

    public ResLoginVO(int id, String email, String username, Byte gender, String roleName, String lastLoginDate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.roleName = roleName;
        this.lastLoginDate = lastLoginDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
