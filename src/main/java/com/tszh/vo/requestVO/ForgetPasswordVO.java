package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
public class ForgetPasswordVO {

    @NotBlank(message = "{ForgetPasswordVO.email.not.blank}")
    @Email(message = "{ForgetPasswordVO.email.illegal.format}")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

