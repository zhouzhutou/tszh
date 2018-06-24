package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tszh.vo.requestVO.ResetPasswordVO;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResResetPasswordVO {

    private String email;

    private String password;

    public ResResetPasswordVO(){

    }

    public ResResetPasswordVO(String email, String password) {
        this.email = email;
        this.password = password;
    }

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
