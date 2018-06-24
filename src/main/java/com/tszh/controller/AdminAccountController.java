package com.tszh.controller;

import com.tszh.entity.User;
import com.tszh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/6/22 0022.
 */
@Controller
@RequestMapping("/")
public class AdminAccountController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin/accountInfo",method = RequestMethod.GET)
    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    public String accountInfo(HttpServletRequest request)
    {
        String email= (String) SecurityUtils.getSubject().getPrincipal();
        User user=userService.findUserByEmail(email);
        request.setAttribute("id",user.getId());
        request.setAttribute("title","账户信息");
        request.setAttribute("contentPath","my_account_info.jsp");
        return "admin/index";
    }

    @RequestMapping(value = "/admin/accountInfo/editAccountInfo",method = RequestMethod.GET)
    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    public String editAccountInfo(HttpServletRequest request)
    {
        String email= (String)SecurityUtils.getSubject().getPrincipal();
        User user=userService.findUserByEmail(email);
        request.setAttribute("id",user.getId());
        request.setAttribute("title","账户信息");
        request.setAttribute("contentPath","edit_my_account_info.jsp");
        return "admin/index";
    }
}
