package com.tszh.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/5/20 0020.
 */
@Controller
@RequestMapping("/")
public class ExchangeItemController {


   /* @RequestMapping(value = "/home/exchangeBookApply")
    public String exchangeBookApply(HttpServletRequest request)
    {
        request.setAttribute("title","置换申请");
        request.setAttribute("contentPath","book_exchange_apply.jsp");
        return "home/index";
    }*/

    @RequestMapping(value = "/home/exchangeBookItemSearch",method = RequestMethod.GET)
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public String exchangeBookItemSearch(HttpServletRequest request)
    {
        request.setAttribute("title","置换记录");
        request.setAttribute("contentPath","book_exchange_item.jsp");
        return "home/index";
    }

}
