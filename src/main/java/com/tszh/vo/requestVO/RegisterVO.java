package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
public class RegisterVO {

    @NotBlank(message = "{RegisterVO.email.not.blank}")
    @Email(message = "{RegisterVO.email.illegal.format}")
    private String email;

    @NotBlank(message = "{RegisterVO.username.not.blank}")
    @Length(max = 30,message = "{RegisterVO.username.max.length}")
    private String username;

    @NotBlank(message = "{RegisterVO.password.not.blank}")
    @Pattern(regexp = "^[a-zA-Z0-9_\\.]{6,30}$",message = "{RegisterVO.password.illegal.format}")
    private String password;

    @NotBlank(message = "{RegisterVO.gender.not.blank}")
    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
