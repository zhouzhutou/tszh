package com.tszh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/4/25 0025.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "/home")
    public String goHome()
    {
        return "home/index";
    }
}
