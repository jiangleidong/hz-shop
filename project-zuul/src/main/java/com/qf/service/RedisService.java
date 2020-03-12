package com.qf.service;

import com.qf.dto.RedisDTO;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-09 23:34
 **/
@Service
public class RedisService {

    @Autowired
    private RestTemplate restTemplate;


    public R set (RedisDTO redisDTO){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个R
        String uri = "http://redis-service/set";
         return restTemplate.postForObject(uri,redisDTO,R.class);
    }
    public R delect(RedisDTO redisDTO){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
        String uri = "http://redis-service/delect";
        return restTemplate.postForObject(uri,redisDTO,R.class);
    }

    public R  get(RedisDTO redisDTO){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
        String uri = "http://redis-service/get";

        return restTemplate.postForObject(uri,redisDTO,R.class);
    }


    //当熔断被打开，那么此方法会被调用
    /*public String hiError(String message){
        return String.format("您的消息:%s未发送成功，请检查您的网络",message);
    }*/



}
