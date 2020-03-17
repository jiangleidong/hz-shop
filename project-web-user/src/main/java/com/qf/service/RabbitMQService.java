package com.qf.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @program: hz-shop
 * @description: rabbit服务
 * @author: Mr.jiang
 * @create: 2020-03-13 14:44
 **/
@Service
public class RabbitMQService {


//发送到rabbit服务服务器上,相当于是生产者

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message){
        rabbitTemplate.convertAndSend("my_topic_exchange","qf.java.apple",message);
    }
}
