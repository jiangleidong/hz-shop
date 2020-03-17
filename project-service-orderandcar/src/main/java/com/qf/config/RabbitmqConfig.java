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

    @Bean("queue10")
    public Queue getQueue10(){
        return new Queue("creatorder");
    }
    @Bean("queue11")
    public Queue getQueue11(){
        return new Queue("creatorderlist");
    }

    @Bean("queue2")
    public Queue getQueue1(){
        return new Queue("delectorder");
    }


    //=========通配符模式=========



    @Bean
    public Binding getBinding2(@Qualifier("queue2") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("qf.java.*");
    }

    @Bean
    public Binding getBinding1(@Qualifier("queue10") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("qf.java.*");
    }

    @Bean
    public Binding getBinding3(@Qualifier("queue11") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("qf.java.*");
    }
    
    

}
