package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
public class ResetPasswordVO {

    @NotBlank(message = "{ResetPasswordVO.email.not.blank}")
    @Email(message = "{ResetPasswordVO.email.illegal.format}")
    private String email;

    @NotBlank(message = "{ResetPasswordVO.password.not.blank}")
    @Pattern(regexp = "^[a-zA-Z0-9_\\.]{6,30}$",message = "{ResetPasswordVO.password.illegal.format}")
    private String password;

    @NotBlank(message = "{ResetPasswordVO.forgetPasswordVerifyCode.not.blank}")
    private String forgetPasswordVerifyCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForgetPasswordVerifyCode() {
        return forgetPasswordVerifyCode;
    }

    public void setForgetPasswordVerifyCode(String forgetPasswordVerifyCode) {
        this.forgetPasswordVerifyCode = forgetPasswordVerifyCode;
    }
}
