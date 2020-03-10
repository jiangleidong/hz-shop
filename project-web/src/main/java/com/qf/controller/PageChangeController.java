package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: hz-shop
 * @description: 页面跳转配置
 * @author: Mr.jiang
 * @create: 2020-03-10 16:58
 **/
@Controller
public class PageChangeController {

    @RequestMapping("/login")
    public String login()  {
        return "login";
    }


    @RequestMapping("/register")
    public String register()  {
        return "register";
    }


}
