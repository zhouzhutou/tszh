package com.tszh.entity;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/21 0021.
 */
public class VerifyEmail extends Email{

    private String authCode;

    private String username;

    public VerifyEmail(){
    }
    public VerifyEmail(String fromAddress, String toAddress, String subject, String contentPath, Date sendDate,String authCode) {
        super(fromAddress,toAddress,subject,contentPath,sendDate);
        this.authCode=authCode;
    }

    public VerifyEmail(String fromAddress, String toAddress, String subject, String contentPath, Date sendDate,String authCode,String username) {
        super(fromAddress,toAddress,subject,contentPath,sendDate);
        this.authCode=authCode;
        this.username=username;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
