package com.tszh.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Controller
@RequestMapping("/")
public class ExchangeBookController {

    @RequestMapping(value = "/home/bookSearch",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String bookSearch(HttpServletRequest request)
    {
        request.setAttribute("title","图书检索");
        request.setAttribute("contentPath","book_search.jsp");
        return "home/index";
    }

    @RequestMapping(value = "/home/myBookSearch",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String myBookSearch(HttpServletRequest request)
    {
        request.setAttribute("title","我的图书");
        request.setAttribute("contentPath","book_my_search.jsp");
        return "home/index";
    }
}
