package com.qf.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 14:49
 **/
@Component
public class RabbitController {

        @RabbitListener(queues = "creatorder")
        public void process(String message){
            System.out.println("接收到creatorder的消息："+message);
        }

    @RabbitListener(queues = "delectorder")
    public void get(String message){
        System.out.println("接收到的delectorder消息："+message);
    }


    }

