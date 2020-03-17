package com.qf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("hi/abc")
    @ResponseBody
    public String sayHi(String message){
//        if(message!=null){
//            throw new RuntimeException();
//        }

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return String.format("当前是来自于%s的消息：%s",serverPort,message);
    }


}
