package com.tszh.service.impl;

import com.tszh.entity.Email;
import com.tszh.entity.ExchangeApplyEmail;
import com.tszh.entity.User;
import com.tszh.entity.VerifyEmail;
import com.tszh.service.EmailService;
import com.tszh.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
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
import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
@Service("emailService")
public class EmaiServiceImpl implements EmailService{

    private static ExecutorService executorService=Executors.newFixedThreadPool(128);

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
    public void sendEmail(Email email)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run(){
                if(email instanceof VerifyEmail) {
                    MimeMessagePreparator preparator = new MimeMessagePreparator() {
                        @Override
                        public void prepare(MimeMessage mimeMessage) throws Exception {
                            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                            messageHelper.setFrom(email.getFromAddress());
                            messageHelper.setTo(email.getToAddress());
                            messageHelper.setSubject(email.getSubject());
                            Map<String, Object> model = new HashMap<>();
                            if(!StringUtils.isBlank(((VerifyEmail)email).getUsername()))
                                model.put("username",((VerifyEmail)email).getUsername());
                            model.put("authCode", ((VerifyEmail) email).getAuthCode());
                            model.put("currentDate", dateUtil.formatDate(email.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, email.getContentPath()
                                    , "utf-8", model);
                            messageHelper.setText(text, true);
                        }
                    };
                    mailSender.send(preparator);
                }else if(email instanceof ExchangeApplyEmail){
                    MimeMessagePreparator preparator = new MimeMessagePreparator() {
                        @Override
                        public void prepare(MimeMessage mimeMessage) throws Exception {
                            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                            messageHelper.setFrom(email.getFromAddress());
                            messageHelper.setTo(email.getToAddress());
                            messageHelper.setSubject(email.getSubject());
                            Map<String, Object> model = new HashMap<>();
                            model.put("exchangeItemId",((ExchangeApplyEmail)email).getExchangeItemId());
                            model.put("applyUserName",((ExchangeApplyEmail)email).getApplyUserName());
                            model.put("myOwnBookName",((ExchangeApplyEmail)email).getMyOwnBookName());
                            model.put("wishBookOwnerName",((ExchangeApplyEmail)email).getWishBookOwnerName());
                            model.put("wishBookName",((ExchangeApplyEmail)email).getWishBookName());
                            model.put("expireDate",dateUtil.formatDate(((ExchangeApplyEmail)email).getExpireDate(),"yyyy-MM-dd"));
                            model.put("mailingAddress",((ExchangeApplyEmail)email).getMailingAddress());
                            model.put("currentDate", dateUtil.formatDate(email.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, email.getContentPath()
                                    , "utf-8", model);
                            messageHelper.setText(text, true);
                        }
                    };
                    mailSender.send(preparator);
                }
            }
        });
    }

    /*@Override
    public void sendRegisterVerifyEmail(Email email) throws InterruptedException
    {
        executorService.execute(new Runnable() {
            @Override
            public void run(){
                if(email instanceof VerifyEmail) {
                    MimeMessagePreparator preparator = new MimeMessagePreparator() {
                        @Override
                        public void prepare(MimeMessage mimeMessage) throws Exception {
                            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                            messageHelper.setFrom(email.getFromAddress());
                            messageHelper.setTo(email.getToAddress());
                            messageHelper.setSubject(email.getSubject());
                            Map<String, Object> model = new HashMap<>();
                            if(!StringUtils.isBlank(((VerifyEmail)email).getUsername()))
                                model.put("username",((VerifyEmail)email).getUsername());
                            model.put("authCode", ((VerifyEmail) email).getAuthCode());
                            model.put("currentDate", dateUtil.formatDate(email.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, email.getContentPath()
                                    , "utf-8", model);
                            messageHelper.setText(text, true);
                        }
                    };
                }
                mailSender.send(preparator);

            }
        });
    }

    @Override
    public void sendForgetPasswordVerifyEmail(Email email) throws InterruptedException {
        blockingQueue.offer(email,600,TimeUnit.SECONDS);
        executorService.execute(new Runnable() {
            @Override
            public void run(){
                try {
                    final Email sendEmail=blockingQueue.poll(600,TimeUnit.SECONDS);
                    MimeMessagePreparator preparator=new MimeMessagePreparator() {
                        @Override
                        public void prepare(MimeMessage mimeMessage) throws Exception {
                            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
                            messageHelper.setFrom(sendEmail.getFromAddress());
                            messageHelper.setTo(sendEmail.getToAddress());
                            messageHelper.setSubject(sendEmail.getSubject());
                            Map<String,Object> model=new HashMap<>();
                            model.put("email",sendEmail.getToAddress());
                            model.put("authCode",sendEmail.getAuthCode());
                            model.put("currentDate",dateUtil.formatDate(sendEmail.getSendDate(),"yyyy-MM-dd HH:mm:ss"));
                            String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,sendEmail.getContentPath()
                                    ,"utf-8",model);
                            messageHelper.setText(text,true);
                        }
                    };
                    mailSender.send(preparator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}

