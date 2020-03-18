package com.qf.controller;

import com.qf.dto.OrderDTO;
import com.qf.dto.Pt;
import com.qf.entity.TOrderdetail;
import com.qf.service.OrdertService;
import com.qf.vo.CartItem;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: hz-shop
 * @description: 订单
 * @author: Mr.jiang
 * @create: 2020-03-16 22:40
 **/

@Controller
public class OrderController {

//    @Autowired
//    private OrdertService ordertService;
//
//    @ResponseBody
//    @RequestMapping("addtoirderdetail")
//    R creatorderlist(){

//        List<TOrderdetail> orderdetail=new ArrayList<TOrderdetail>();
//        TOrderdetail tOrderdetail=new TOrderdetail(,null,"12345",123,Long.valueOf(998),"鞋子",100,"img");
//        TOrderdetail tOrderdetail2=new TOrderdetail(null,"12345",123,Long.valueOf(299),"衣服",55,"pig");
//        orderdetail.add(tOrderdetail);
//        orderdetail.add(tOrderdetail2);
//        System.out.println(orderdetail);
//        int cou = ordertService.insert(orderdetail);
//        System.out.println(cou);
//        return R.ok(200,"成功",cou);
//    };



}
