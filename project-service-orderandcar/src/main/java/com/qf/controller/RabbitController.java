package com.qf.controller;

import com.qf.dto.OrderDTO;
import com.qf.dto.Pt;
import com.qf.entity.TOrder;
import com.qf.entity.TProduct;
import com.qf.service.OrdertService;
import com.qf.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 14:49
 **/
@Component
public class RabbitController {


    @Autowired
    private ProductService productService;

    @Autowired
    private OrdertService orderService;

        @RabbitListener(queues = "creatorder")
        public void process(OrderDTO orderDTO){


            //创建一天订单信息并存到数据库  数据库有
            List<Pt> pts = orderDTO.getPts();
            //通过pid去获取product的数据,如果没有就到数据库中去找  然后将数据相乘 ,返回和
            Long sum=null;
            for (Pt pt : pts) {
                int count = pt.getCount();

                //设置一下,先去redis找,找不到再去数据去找  还没有完成
                TProduct tProduct = productService.getone(pt.getPid());
                Long price = tProduct.getPrice();
                sum=sum+price*count;
            }
            TOrder torder=new TOrder();
            torder.setOid(orderDTO.getOrderno());
            torder.setName(orderDTO.getName());
            torder.setPhone(orderDTO.getPhone());
            torder.setAddress(orderDTO.getAddress());
            torder.setCreatdata(new Date());
            torder.setUid(orderDTO.getId());
            torder.setTotalprice(sum);
            torder.setStatue(0);

            int getone = orderService.getone(torder);
              //如果int=1,即表示添加数据库完成

        }



    @RabbitListener(queues = "delectorder")
    public void get(String message){
        System.out.println("接收到的delectorder消息："+message);
    }


    }

