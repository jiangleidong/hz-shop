package com.qf.controller;

import com.qf.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: hz-shop
 * @description: 购物车的controller层
 * @author: Mr.jiang
 * @create: 2020-03-12 19:16
 **/
@Controller
@RequestMapping("cart")
public class ShopCartController {


  /*
  * 添加商品到购物车的程序
  * */
    @RequestMapping("addshopcart")
    @ResponseBody
    public R addshopcart(Long pid , Integer count, HttpServletRequest request ){

        if(pid==null||count<0||pid==null){ return  R.error("未选中商品或数量");}
        Object o = request.getAttribute("user");

        System.out.println(o);
        /*
        * 接下来执行添加购物车逻辑
        * 1,查看 用户是否登录
        * 没登陆的话 通过擦cookie看之前是否已经有购物车了
        * 将购物车整合之后重新存入 cookie   和redis
        *
        * 2,用户是登录的
        *
        *
        * */







        return new R();
    }



    //通过userid  去查询购物车信息



}
