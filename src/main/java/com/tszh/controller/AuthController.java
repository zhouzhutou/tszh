package com.tszh.controller;

import com.tszh.entity.User;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * Created by Administrator on 2018/4/18 0018.
 */
@Controller
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
    public String login()
    {
        return "auth/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginAction(@RequestParam(value = "email",required = true) String email,
                              @RequestParam(value = "password",required = true) String password,
                              HttpServletRequest request, HttpServletResponse servletResponse)
    {
        System.out.println("123456");
        return "home/index";
        /*ModelAndView mav=new ModelAndView();
        HttpSession session=request.getSession();
        User currentUser=(User)session.getAttribute("currentUser");
        System.out.println(currentUser);
        if(currentUser!=null ){
            mav.setViewName("redirect:/home");
            return mav;
        }
        User user=userService.findUserByEmail(email);
        if(user==null || !StringUtils.equals(cryptographyUtil.md5(password,user.getSalt()),user.getPassword())){
            mav.setViewName("auth/login");
            mav.addObject("email",email);
            mav.addObject("passowrd",password);
            mav.addObject("errorMsg","用户名或密码错误");
            return mav;
        }else{
            user.setLastLoginDate(new Date());
            userService.save(user);
            session.setAttribute("currentUser",user);
            mav.setViewName("redirect:/home");
            return mav;
        }*/
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized()
    {
        return "view/auth/unauthorized";
    }

}
