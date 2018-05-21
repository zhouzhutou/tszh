package com.tszh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/25 0025.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "/home")
    public String goHome(HttpServletRequest request)
    {
        request.setAttribute("title","首页");
        request.setAttribute("contentPath","book_search.jsp");
        return "home/index";
    }

}
