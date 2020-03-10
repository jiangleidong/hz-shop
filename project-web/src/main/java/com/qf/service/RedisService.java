package com.qf.service;

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


    public R set (String key,String value){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个R
        String uri = "http://redis-service/set?key="+key+"&value="+value;
         return restTemplate.getForObject(uri,R.class);
    }
    public R delect(String key){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
        String uri = "http://redis-service/delect?key="+key;
        return restTemplate.getForObject(uri, R.class);
    }

    public R  get(String key){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
        String uri = "http://redis-service/get?key="+key;
        restTemplate.getForObject(uri,String.class);
        return restTemplate.getForObject(uri,R.class);
    }


    //当熔断被打开，那么此方法会被调用
    /*public String hiError(String message){
        return String.format("您的消息:%s未发送成功，请检查您的网络",message);
    }*/



}
