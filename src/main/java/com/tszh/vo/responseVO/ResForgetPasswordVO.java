package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2018/6/19 0019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResForgetPasswordVO {

    private String email;

    public ResForgetPasswordVO(){

    }

    public ResForgetPasswordVO(String email){
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

