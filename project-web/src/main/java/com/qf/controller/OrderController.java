package com.qf.controller;

import com.qf.dto.OrderDTO;
import com.qf.dto.Pt;
import com.qf.entity.TUser;
import com.qf.service.RabbitMQService;
import com.qf.service.UserService;
import com.qf.util.Creatno;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-16 23:57
 **/
@Controller
public class OrderController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private UserService service;


    @RequestMapping("createorder")
    public R creatOrder(OrderDTO orderDTO){
        String address = orderDTO.getAddress();
        String name = orderDTO.getName();
        int phone = orderDTO.getPhone();
        List<Pt> pts = orderDTO.getPts();

        //判断用户是否登录,如果登录才能生成订单
        TUser getusermessage = service.getusermessage();

        if (getusermessage==null){
            return R.error("登录已过期");
        }

        //将用户数据封装到orderdto


        Long id = getusermessage.getId();
        orderDTO.setId(id);

        //生成订单编号
        String creatnumber = Creatno.creatnumber();
        orderDTO.setOrderno(creatnumber);


        //将信息给rabbitMQ   让他们去接收,分别执行生成订单,生成详情表,删除原先购物车里的数据
        rabbitMQService.send(orderDTO);
        return R.ok();
    }


}
