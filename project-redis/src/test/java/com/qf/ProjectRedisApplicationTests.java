package com.qf;

import com.qf.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class ProjectRedisApplicationTests {
    @Autowired
    private RedisService redisService;
    @Test
    void contextLoads() {

        long time=60;
        redisService.setAndExpireBySecond("ABC","ABC",time, TimeUnit.SECONDS);
        System.out.println("success");


    }

    @Test
    void get() {

        long time=60;
        Object test = redisService.getAndExpireBySecond("ABC", time,TimeUnit.SECONDS);
        System.out.println(test);


    }
    @Test
    void delect() {

        Boolean test = redisService.delect("ABC");
        System.out.println(test);


    }



}
