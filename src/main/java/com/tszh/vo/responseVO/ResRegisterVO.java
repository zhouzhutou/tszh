package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResRegisterVO {

    private String email;

    private String authCode;

    public ResRegisterVO(){}

    public ResRegisterVO(String email, String authCode)
    {
        this.email=email;
        this.authCode=authCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
