package com.tszh.service.impl;

import com.tszh.entity.User;
import com.tszh.service.EmailService;
import com.tszh.util.DateUtil;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
@Service("emailService")
public class EmaiServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    @Qualifier("mailProperties")
    private Properties mailProperties;

    @Autowired
    private DateUtil dateUtil;

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public void setMailProperties(Properties mailProperties) {
        this.mailProperties = mailProperties;
    }

    public DateUtil getDateUtil() {
        return dateUtil;
    }

    public void setDateUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @Override
    public void sendValidateEmail(String subject,String email, String authCode)
    {
        MimeMessagePreparator preparator=new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message=new MimeMessageHelper(mimeMessage);
                //message.setTo();
                message.setTo(email);
                System.out.println("username:");
                System.out.println(mailProperties.getProperty("mail.smtp.username"));
                message.setFrom(mailProperties.getProperty("mail.smtp.username"));
                message.setSubject(subject);
                Map model=new HashMap();
                model.put("authCode",authCode);
                model.put("currentData",dateUtil.formatDate(new Date(),
                        "yyyy-MM-dd hh:mm:ss"));
               // String text=  VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                //        "/view/mail/register.vm",model);
                message.setText("ohhhhhhhhhhh");
            }
        };
        System.out.println("send.....");
        mailSender.send(preparator);
    }
}
