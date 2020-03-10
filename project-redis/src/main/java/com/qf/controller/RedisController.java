package com.qf.controller;

import com.qf.service.RedisService;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;


/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-09 22:56
 **/
@Controller
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("set")
    @ResponseBody
    public R set(String key, String value){
        long time=300;
        redisService.setAndExpireBySecond(key,value,time, TimeUnit.SECONDS);

        return  R.ok("添加到redis成功");
    }

    @RequestMapping("/delect")
    @ResponseBody
    public R delect(String key){

        Boolean delect1 = redisService.delect(key);
        if(!delect1){
            return  R.error("删除失败");
        }
        return  R.ok("删除成功");

    }

    @RequestMapping("/get")
    @ResponseBody
    public R get(String key){
        long time=300;
        String value = (String) redisService.getAndExpireBySecond(key, time, TimeUnit.SECONDS);
        return  R.ok(value,"100",200);
    }




}
