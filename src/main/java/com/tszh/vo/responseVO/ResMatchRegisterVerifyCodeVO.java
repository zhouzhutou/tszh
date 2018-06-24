package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResMatchRegisterVerifyCodeVO {

    private int id;

    private String email;

    private String username;

    private String password;

    private byte sex;

    public ResMatchRegisterVerifyCodeVO(){

    }

    public ResMatchRegisterVerifyCodeVO(int id, String email, String username, String password, byte sex) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.sex = sex;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }
}
