package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.service.IUserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyStore;

/**
 * @program: hz-shop
 * @description: USER的controller层
 * @author: Mr.jiang
 * @create: 2020-03-09 21:57
 **/

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Value("${server.port}")
    private String serverPort;


    @RequestMapping("getone")
    @ResponseBody
    public TUser getuserbyid(Long id){
        TUser tUser = userService.selectbyid(id);
//
        return tUser;
}

    /*验证用户登录的信息*/
    @RequestMapping("/login")
    @ResponseBody
    public TUser login(@RequestBody TUser tuser){
        TUser tU = userService.checkUser(tuser);

        return tU;
    }

    /*存入用户的信息*/
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody TUser tuser){
        int insert = userService.insert(tuser);

        if(insert!=1){
            return R.error();
        }
        return R.ok();
    }

    /*通过邮件查询修改 用户的信息*/
    @RequestMapping("/updatebyemail")
    @ResponseBody
    public R updatebyemail(@RequestBody TUser tuser){
        int insert = userService.updateByEmailSelective(tuser);

        if(insert!=1){
            return R.error();
        }
        return R.ok();
    }



}
