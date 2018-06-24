package com.tszh.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
@Controller
@RequestMapping("/")
public class AdminExchangeBookController {

    @RequestMapping(value = "/admin/bookManage",method = RequestMethod.GET)
    @RequiresRoles("admin")
    public String bookManage(HttpServletRequest request)
    {
        request.setAttribute("title","图书管理");
        request.setAttribute("contentPath","book_manage.jsp");
        return "admin/index";
    }
}
