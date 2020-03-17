package com.qf.service;


import com.qf.dto.RedisDTO;
import com.qf.exception.ProjectException;
import com.qf.vo.R;
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
public class RedisService {
    @Autowired
    private RedisTemplate<Object,Object> template;


    public R set(RedisDTO redisDTO) {
        try {
            template.opsForValue().set(redisDTO.getKey(), redisDTO.getValue(), redisDTO.getTime(), TimeUnit.SECONDS);
        }catch (Exception ex){
            //log.error("【存数据到Redis发生问题】 存储失败!!! key = {} , value = {}",key,value);
            throw new ProjectException(777,"数据存储到Redis发生异常!!!");
        }
        return R.ok();
    }




    public R get(RedisDTO redisDTO) {
        try{
            Object obj = template.opsForValue().get(redisDTO.getKey());
            if(redisDTO.getTime()!=null){
            template.expire(redisDTO.getKey(),redisDTO.getTime(), TimeUnit.SECONDS);
            }
            if(obj!=null){
            return R.ok(obj);
            }
            return R.error("没有从redis获取到数据");
        }catch(Exception ex){
           // log.error("【从Redis获取数据失败】 读取数据失败!!! key = {} , value = {}");
            throw new ProjectException(777,"从Redis获取数据失败!!!");
        }
    }

    public Boolean delect(RedisDTO redisDTO) {
        Boolean delete = template.delete(redisDTO.getKey());
        return delete;
    }

}
