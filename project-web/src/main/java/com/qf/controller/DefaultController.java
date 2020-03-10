package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-10 14:26
 **/

@Controller
@CrossOrigin(allowCredentials = "true")
public class DefaultController {


    @RequestMapping("/")
    public String index()  {
        return "index";
    }


}
