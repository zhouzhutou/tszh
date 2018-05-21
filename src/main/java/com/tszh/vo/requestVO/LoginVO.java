package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Administrator on 2018/5/3 0003.
 */
public class LoginVO {

    @NotBlank(message = "{LoginVO.email.not.blank}")
    private String email;

    @NotBlank(message = "{LoginVO.password.not.blank}")
    private String password;

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
}
