package com.qf.service.impl;


import com.qf.exception.ProjectException;
import com.qf.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: dubbo-jxc
 * @description: 这是redisService的实现类
 * @author: Mr.jiang
 * @create: 2020-02-22 15:44
 **/

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<Object,Object> template;

    @Override
    public void setAndExpireBySecond(Object key, Object value, Long timeout, TimeUnit seconds) {
        try {
            template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }catch (Exception ex){
            //log.error("【存数据到Redis发生问题】 存储失败!!! key = {} , value = {}",key,value);
            throw new ProjectException(777,"数据存储到Redis发生异常!!!");
        }
    }



    @Override
    public Object getAndExpireBySecond(Object key, Long timeout, TimeUnit seconds) {
        try{
            Object obj = template.opsForValue().get(key);
            template.expire(key,timeout, TimeUnit.SECONDS);
            return obj;
        }catch(Exception ex){
           // log.error("【从Redis获取数据失败】 读取数据失败!!! key = {} , value = {}");
            throw new ProjectException(888,"从Redis获取数据失败!!!");
        }
    }

    @Override
    public Boolean delect(Object key) {
        Boolean delete = template.delete(key);
        return delete;
    }

}
