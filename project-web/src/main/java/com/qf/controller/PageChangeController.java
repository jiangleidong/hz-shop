package com.qf.controller;

import com.beust.jcommander.IVariableArity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hz-shop
 * @description: 页面跳转配置
 * @author: Mr.jiang
 * @create: 2020-03-10 16:58
 **/
@Controller
public class PageChangeController {

    @RequestMapping("/login")
    public String login(Model model)  {
        return "login";
    }


    @RequestMapping("/register")
    public String register()  {
        return "register";
    }

    @RequestMapping("/shopcart")
    public String shopcart()  {
        return "shopcart";
    }
}
