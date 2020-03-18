package com.qf.controller;


import com.qf.service.CartService;
import com.qf.service.RedisService;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-15 21:01
 **/
@Controller
public class ShopcartController {

         @Autowired
   private CartService cartService;



        @ResponseBody
        @RequestMapping("addproduct")
    R addProduct(String id, Long pid, int count){

            R r = cartService.addProduct(id, pid, count);
            return r;
    };


    @ResponseBody
    @RequestMapping("clean")
    R clean(String id){
        return cartService.clean(id);
    };

    @ResponseBody
    @RequestMapping("update")
    R update(String id, Long pid, int count){
       return cartService.update(id,pid,count);
    };

    @ResponseBody
    @RequestMapping("showcart")
    R showCart(String id){
        return cartService.showCart(id);
    };

    @ResponseBody
    @RequestMapping("merge")
    R merge(Long id, String keyid){
        return cartService.merge(id,keyid); };




}
