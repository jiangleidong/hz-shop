package com.qf.controller;

import com.qf.entity.TUser;

import com.qf.service.ShopCartService;
import com.qf.service.UserService;
import com.qf.vo.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @program: hz-shop
 * @description: 购物车的controller层
 * @author: Mr.jiang
 * @create: 2020-03-12 19:16
 **/
@Controller
@RequestMapping("/cart")
public class ShopCartController {

    @Autowired
    private UserService service;

    @Autowired
    private ShopCartService shopCartService;

    /*
  * 添加商品到购物车的程序
  * */
    @RequestMapping("addproduct")
    @ResponseBody
    public R addshopcart(Long pid ,
                         Integer count,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @CookieValue(name = "shopcart-key", required = false) String shopcartkey){

        if(pid==null||count<0){ return  R.error("未选中商品或数量");}

        TUser getusermessage = service.getusermessage();

        if(getusermessage!=null) {
            //================已登录状态下的购物车======================= redis:    user:cart:userId
            //Long userId = user.getId();
            // return cartService.addProduct(userId.toString(),productId,count);
            Long id = getusermessage.getId();
            String s = String.valueOf(id);
            R r1 = shopCartService.addProduct(s, pid, count);

            if (r1.getCode() == 200) {
                return R.ok("添加数据库成功");
            }

        }
        //==============未登录状态下的购物车=================
        //把该商品添加到购物车，这个购物车是在redis中。


        if(shopcartkey==null){
            //uuid为空的话再生成一个uuid放到cookie里给客户端
            shopcartkey = UUID.randomUUID().toString().replaceAll("-", "");

            //返回该uuid给cookie
            Cookie cookie = new Cookie("shopcart-key",shopcartkey);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //  TODO 执行添加购物车的方法

        R r2 = shopCartService.addProduct(shopcartkey,pid,count);
        if(r2.getCode()==200){
            return R.ok("添加数据库成功");

        }
        return R.error("添加数据库失败");
    }






    /**
     * 清空购物车
     * @param keyid
     * @param response
     * @return
     */
    @RequestMapping("clean")
    @ResponseBody
    public R cleanCart(@CookieValue(name="shopcart-key",required = false)String keyid,
                                HttpServletResponse response,
                                HttpServletRequest request){
        TUser getusermessage = service.getusermessage();
        if(getusermessage!=null){
            //===========已登录状态下的购物车============
            return shopCartService.clean(String.valueOf(getusermessage.getId()));
        }
        //===========未登录状态下的购物车===========
        if(keyid!=null&&!"".equals(keyid)){
            //删除Cookie
            Cookie cookie = new Cookie("shopcart-key","");
            cookie.setMaxAge(0);
            cookie.setPath("/");//admin.qf.com  sso.qf.com  ****.qf.com
            // cookie.setDomain("qf.com");//如果我们使用域名来访问，那么cookie不会被携带，只有这边设置了这个一级域名，qf.com,那么在该域名下的所有二级cookie就都可以携带该cookie
            response.addCookie(cookie);

            //删除redis中的购物车
            return shopCartService.clean(keyid);
        }
        return R.error("当前用户没有购物车");

    }

    /**
     * 更新购物车中的商品
     * @param productId
     * @param count
     * @param uuid
     * @return
     */
    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public R updateCart(
            @PathVariable Long productId,
            @PathVariable int count,
            @CookieValue(name="shopcart-key",required = false)String uuid,
            HttpServletRequest request
    ){

        TUser getusermessage = service.getusermessage();
        if(getusermessage!=null){
            //=============已登录状态下的购物车==============  user:cart:userId

            return shopCartService.update(String.valueOf(getusermessage.getId()),productId,count);

        }

        return shopCartService.update(uuid,productId,count);

    }


    /**
     * 展示购物车
     * @param shopcartkey
     * @param request
     * @return
     */
    @RequestMapping("show")
    @ResponseBody
    public R showCart(@CookieValue(name="shopcart-key",required = false)String shopcartkey,
                      HttpServletRequest request){

        TUser getusermessage = service.getusermessage();
        //============查看已登录状态下的购物车=============
        if(getusermessage!=null){


            return shopCartService.showCart(String.valueOf(getusermessage.getId()));
        }
        if(shopcartkey==null||"".equals(shopcartkey)){


            return R.ok("没有购物车");
        }


        //============查看未登录状态下的购物车=============


        R r = shopCartService.showCart(shopcartkey);
        return r;

    }

    /**
     * 合并两种状态下的购物车
     * @param shopcartkey
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("merge")
    @ResponseBody
        public R merge(@CookieValue(name="shopcart-key",required = false)String shopcartkey,
                            HttpServletRequest request,
                       HttpServletResponse response) {
        //获得uuid,和uid
        TUser getusermessage = service.getusermessage();
        Long id = null;
        if (getusermessage != null) {
            id = getusermessage.getId();
        }


        //做完合并以后，要把未登录状态下的购物车清空。在清空cookie
        Cookie cookie = new Cookie("shopcart-key", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);


        return shopCartService.merge(id, shopcartkey);
    }


}








