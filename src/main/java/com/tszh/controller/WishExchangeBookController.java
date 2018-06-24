package com.tszh.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/5/28 0028.
 */
@Controller
@RequestMapping("/")
public class WishExchangeBookController {

    @RequestMapping(value = "/home/haveReadBooks",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String haveReadBooks(HttpServletRequest request){
        request.setAttribute("title","已阅图书");
        request.setAttribute("contentPath","book_have_read.jsp");
        return "home/index";
    }

    @RequestMapping(value = "/home/toReadBooks",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String toReadBooks(HttpServletRequest request){
        request.setAttribute("title","待阅图书");
        request.setAttribute("contentPath","book_to_read.jsp");
        return "home/index";
    }
}
