package com.tszh.service;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
public interface EmailService {

    /**
     * 发送验证邮件
     * @param subject
     * @param email
     * @param authCode
     */
    public void sendValidateEmail(String subject,String email, String authCode);
}
