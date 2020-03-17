package com.qf.controller;

import com.qf.dto.RedisDTO;
import com.qf.service.RedisService;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    public R set(@RequestBody RedisDTO redisDTO){

        redisService.set(redisDTO);

        return  R.ok("添加到redis成功");
    }

    @RequestMapping("/delect")
    @ResponseBody
    public R delect(@RequestBody RedisDTO redisDTO){

        Boolean delect1 = redisService.delect(redisDTO);


        if(!delect1){
            return  R.error("删除失败");
        }
        return  R.ok("删除成功");

    }

    @RequestMapping("/get")
    @ResponseBody
    public R get(@RequestBody RedisDTO redisDTO){
        Object value =  redisService.get(redisDTO);

        return  R.ok(200,"成功",value);
    }




}
