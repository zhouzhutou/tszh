package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
public class RegisterVO implements Serializable{

    @NotBlank(message = "{RegisterVO.email.not.blank}")
    @Email(message = "{RegisterVO.email.illegal.format}")
    private String email;

    @NotBlank(message = "{RegisterVO.username.not.blank}")
    @Length(max = 32,message = "{RegisterVO.username.max.length}")
    private String username;

    @NotBlank(message = "{RegisterVO.password.not.blank}")
    @Pattern(regexp = "^[a-zA-Z0-9_\\.]{6,30}$",message = "{RegisterVO.password.illegal.format}")
    private String password;

    @NotNull(message = "{RegisterVO.gender.not.null}")
    private Byte gender;

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

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterVO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                '}';
    }
}
