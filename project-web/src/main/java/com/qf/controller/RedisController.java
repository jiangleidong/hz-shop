package com.qf.controller;

import com.qf.service.RedisService;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-09 23:31
 **/
@Controller
@RequestMapping("/redis")
public class RedisController {

//    @Autowired
//    private RedisService redisService;
//
//    @RequestMapping("/set")
//    @ResponseBody
//    public R set(String key, String value,Long time){
//        return redisService.set(key,value,time);
//    }
//
//
//    @RequestMapping("/delect")
//    @ResponseBody
//    public R delect(String key){
//        return redisService.delect(key);
//    }
//    @RequestMapping("/get")
//    @ResponseBody
//    public R get(String key,Long time){
//      return   redisService.get(key,time);
//    }

}
