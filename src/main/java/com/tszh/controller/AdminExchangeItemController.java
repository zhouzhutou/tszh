package com.tszh.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/6/21 0021.
 */
@Controller
@RequestMapping("/")
public class AdminExchangeItemController {

    @RequestMapping(value = "/admin/exchangeItemManage",method = RequestMethod.GET)
    @RequiresRoles("admin")
    public String exchangeItemManage(HttpServletRequest request){
        request.setAttribute("title","置换管理");
        request.setAttribute("contentPath","exchange_item_manage.jsp");
        return "admin/index";
    }
}
