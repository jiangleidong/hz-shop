package com.qf.controller;

import com.qf.constant.StaticConstant;
import com.qf.entity.TUser;
import com.qf.service.RedisService;
import com.qf.service.UserService;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-09 22:18
 **/

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getone")
    @ResponseBody
    public String getone(Long id){

        return service.getone(id);
    }


    /*登录功能
     * user登录验证,并把登录的user信息放到redis中,设置市场半小时过期*/
    @RequestMapping("/login")
    @ResponseBody
    public R login(TUser tuser, HttpServletResponse resp, @CookieValue(value = "user-key",required = false)String UserKey){
        //判断有没有值传入
        if (tuser.getUname()==null||tuser.getPassword()==null){
            return R.error("账号密码未填写");
        }
        //验证密码的正确性
        R login = service.login(tuser);

        //不正确的情况
        if (login.getCode()==0){
            return R.error("账号密码不正确");
        }
        //正确情况,封装用户信息,存入一个session,存到redis中,设置半小时有效期
        String id = login.getData().toString();
        // 如果jxcUserKey是null 生成一个
        if(UserKey == null){
            UserKey = UUID.randomUUID().toString().replaceAll("-","");
        }
        //存入的键值对    key  为 随机的session值      ,value为uid
        String key = UserKey;
        String value = id;
        redisService.set(key,value);

        // 写回jxc-user-key
        Cookie cookie = new Cookie("user-key", UserKey);
        cookie.setMaxAge(9999999);
        resp.addCookie(cookie);

        return R.ok("密码正确");
    }



    /*邮箱注册功能
     * 注册ok后发送验证短信验证登录验证*/
    @RequestMapping("/register")
    @ResponseBody
    public R register(TUser tuser, HttpServletResponse resp, @CookieValue(value = "user-key",required = false)String UserKey){


        //判断是邮箱注册还是短信注册

        if(tuser.getEmail()!=null){
        //邮箱注册
            if (tuser.getEmail()==null||tuser.getPassword()==null){
                return R.error("填写错误,请重新填写");
            }
            //此时邮箱已经在了,现在需要调用服务完成任务
            //1.发送一份激活验证的邮件



            String uuid=UUID.randomUUID().toString();
            R sender = service.sender(tuser, uuid);
            if(sender.getCode()==0){
                return R.error("发送邮箱失败");
            }
            //2,将用户信息存入到数据库中,此时激活状态fales
            tuser.setFlag(false);
            boolean save = service.save(tuser);
            if (!save){
                return R.error("存入mysql数据库发生了错误");
            }

            //3,存入一个时常为30分的redis键值对
             //时间还没设定
            R set = redisService.set(uuid, tuser.getEmail());
            if (set.getCode()!=200){
                return R.error("存入redis数据库发生了错误");
            }


            //4,同时也存入一个市场为30分钟的cookie
            // 写回jxc-user-key
            Cookie cookie = new Cookie("active-key", uuid);
            cookie.setMaxAge(1800);
            resp.addCookie(cookie);


            //5,返回前端信息让他去激活邮箱链接
            R.ok("请激活邮箱,有效时常30分钟,逾期不候");



        }




        //设置为还未完成验证
        tuser.setFlag(false);
        //1,将这个tuer存入数据库中


         //2,发送一封激活邮件



        //返回前端数据



        return R.ok("密码正确");
    }



   /* @RequestMapping("getCookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+"="+cookie.getValue());
        }
        return "ok";

    }*/

}
