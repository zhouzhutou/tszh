package com.tszh.service;

import com.tszh.entity.Email;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param email
     */
    public void sendEmail(Email email);

    /**
     * 发送注册验证邮件
     * @param email
     */
    //public void sendRegisterVerifyEmail(Email email) throws InterruptedException;

    /**
     * 发送密码重置验证邮件
     * @param email
     * @throws InterruptedException
     */
    //public void sendForgetPasswordVerifyEmail(Email email)  throws InterruptedException;
}
