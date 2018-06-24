package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResRegisterVO {

    private String email;

    private String username;

    private Byte gender;

    public ResRegisterVO(){

    }

    public ResRegisterVO(String email, String username, Byte gender) {
        this.email = email;
        this.username = username;
        this.gender = gender;
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
}
