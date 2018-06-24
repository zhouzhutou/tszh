package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
public class MatchRegisterVerifyCodeVO {

    @NotBlank(message = "{MatchRegisterVerifyCodeVO.email.not.blank}")
    @Email(message = "{MatchRegisterVerifyCodeVO.email.illegal.format}")
    private String email;

    @NotBlank(message = "{MatchRegisterVerifyCodeVO.registerVerifyCode.not.blank}")
    private String registerVerifyCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterVerifyCode() {
        return registerVerifyCode;
    }

    public void setRegisterVerifyCode(String registerVerifyCode) {
        this.registerVerifyCode = registerVerifyCode;
    }

    @Override
    public String toString() {
        return "MatchRegisterVerifyCodeVO{" +
                "email='" + email + '\'' +
                ", registerVerifyCode='" + registerVerifyCode + '\'' +
                '}';
    }
}
