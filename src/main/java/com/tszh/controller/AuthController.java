package com.tszh.controller;

import com.tszh.cons.Code;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * Created by Administrator on 2018/4/18 0018.
 */
@Controller()
@RequestMapping("/")
public class AuthController {


    @Autowired
    private CryptographyUtil cryptographyUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String start()
    {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated())
        {
            if (subject.hasRole("user"))
                return "redirect:/home";
        }
        request.setAttribute("title","登录");
        request.setAttribute("contentPath","login.jsp");
        return "auth/auth_common";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpServletRequest request)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated())
        {
            if (subject.hasRole("user"))
                return "redirect:/home";
        }
        request.setAttribute("title","注册");
        request.setAttribute("contentPath","register.jsp");
        return "auth/auth_common";
    }

    @RequestMapping(value = "/matchRegisterVerifyCode",method = RequestMethod.GET)
    public String matchRegisterVerifyCode(@RequestParam("email") String email,HttpServletRequest request)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("user"))
                return "redirect:/home";
        }
        request.setAttribute("email",email);
        request.setAttribute("title","注册");
        request.setAttribute("contentPath","matchRegisterVerifyCode.jsp");
        return "auth/auth_common";
    }

    @RequestMapping(value = "/forgetPassword",method = RequestMethod.GET)
    public String forgetPassword(HttpServletRequest request)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("user"))
                return "redirect:/home";
        }
        request.setAttribute("title","忘记密码");
        request.setAttribute("contentPath","forgetPassword.jsp");
        return "auth/auth_common";
    }

    @RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
    public String resetPassowrd(@RequestParam("email") String email, HttpServletRequest request)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("user"))
                return "redirect:/home";
        }
        request.setAttribute("email",email);
        request.setAttribute("title","重置密码");
        request.setAttribute("contentPath","resetPassword.jsp");
        return "auth/auth_common";
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized()
    {
        return "auth/unauthorized";
    }

}
