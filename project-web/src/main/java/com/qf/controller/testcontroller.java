package com.qf.controller;

import com.google.gson.Gson;
import com.netflix.ribbon.proxy.annotation.Http;
import com.qf.entity.TUser;
import com.qf.service.RabbitMQService;
import com.qf.vo.R;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 14:50
 **/
@Controller
public class testcontroller {

    @Autowired
    private RabbitMQService rabbitMQService;

    @RequestMapping("getmessage")
    @ResponseBody
    public String getmessage( String message){

        rabbitMQService.send(message);

        return  "";
    }


    @Autowired
    private HttpServletRequest request;


    @RequestMapping("getuser")
    @ResponseBody
    public R getusermessage(HttpServletResponse resp, @CookieValue(value = "user-key", required = false) String UserKey){

        String header= request.getHeader("user");
        System.out.println(header);
        Gson gson = new Gson();
        TUser user = gson.fromJson( header , TUser.class);
        System.out.println(user);

        return  R.ok(user);
    }
}
