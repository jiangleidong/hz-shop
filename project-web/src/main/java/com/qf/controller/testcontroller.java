package com.qf.controller;

import com.google.gson.Gson;
import com.netflix.ribbon.proxy.annotation.Http;
import com.qf.dto.OrderDTO;
import com.qf.dto.Pt;
import com.qf.entity.TUser;
import com.qf.service.RabbitMQService;
import com.qf.vo.R;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 14:50
 **/
@Controller
public class testcontroller {

    @Autowired
    private RabbitMQService rabbitMQService;


    @RequestMapping("getmessage")
    @ResponseBody
    public String getmessage( String message){

        rabbitMQService.send(message);

        return  "";
    }


    @Autowired
    private HttpServletRequest request;


    @RequestMapping("getuser")
    @ResponseBody
    public R getusermessage(HttpServletResponse resp, @CookieValue(value = "user-key", required = false) String UserKey){

        String header= request.getHeader("user");
        System.out.println(header);
        Gson gson = new Gson();
        TUser user = gson.fromJson( header , TUser.class);
        System.out.println(user);

        return  R.ok(user);
    }

    @RequestMapping("testrabbit")
    @ResponseBody
    public void getusermessage(){
        OrderDTO orderDTO = new OrderDTO();
        //用户id
        orderDTO.setId(Long.valueOf(6));
        UUID uuid = UUID.randomUUID();

        orderDTO.setName("姜磊栋");
        orderDTO.setOrderno(uuid.toString());

        List<Pt> pts=new ArrayList<Pt>();
        //数量为1,商品id844033
        pts.add(new Pt(2,Long.valueOf(844033)));

        pts.add(new Pt(1,Long.valueOf(844034)));
        orderDTO.setPts(pts);

        rabbitMQService.send(orderDTO);


    }

}
