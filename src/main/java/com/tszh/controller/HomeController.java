package com.tszh.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/25 0025.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String goHome(HttpServletRequest request)
    {
        request.setAttribute("title","首页");
        request.setAttribute("contentPath","book_search.jsp");
        return "home/index";
    }

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    @RequiresRoles("admin")
    public String goAdminHome(HttpServletRequest request){
        request.setAttribute("title","首页");
        request.setAttribute("contentPath","book_manage.jsp");
        return "admin/index";
    }

}
