package com.qf.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {


    //生产者部分
    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("my_fanout_exchange",true,false,null);
    }

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange("my_direct_exchange");
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("my_topic_exchange",true,false);
    }




    //消费者部分

    /*
    1.创建队列
    2.创建交换机
    3.将队列绑定在交换机上
     */

//    @Bean
//    public Queue getQueue(){
//        return new Queue("my_topic_queue");
//    }
//
//    @Bean
//    public Queue getQueue2(){
//        return new Queue("my_topic_queue2");
//    }
//
//    //=========通配符模式=========
//
//
//
//    @Bean
//    public Binding getBinding(@Qualifier("getQueue2") Queue queue, TopicExchange topicExchange){
//        return BindingBuilder.bind(queue).to(topicExchange).with("qf.java.apple");
//    }


}
