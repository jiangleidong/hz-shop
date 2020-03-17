package com.qf.controller;

import com.qf.constant.Constant;
import com.qf.dto.RedisDTO;
import com.qf.entity.TUser;
import com.qf.service.RedisService;
import com.qf.service.UserService;
import com.qf.util.StringAppend;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-09 22:18
 **/

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getone")
    @ResponseBody
    public String getone(Long id) {

        return service.getone(id);
    }
    @RequestMapping("user/getone")
    @ResponseBody
    public String getone2(Long id) {

        return service.getone(id);
    }


    /*登录功能
     * user登录验证,并把登录的user信息放到redis中,设置市场半小时过期*/
    @RequestMapping("/login2")
    @ResponseBody
    public R login(TUser tuser, HttpServletResponse resp, @CookieValue(value = "user-key", required = false) String UserKey) {
        //判断有没有值传入
        if (tuser.getUname() == null || tuser.getUname() == "" || tuser.getPassword() == null || tuser.getPassword() == "") {
            return R.error(2, "账号密码未填写");
        }
        //验证密码的正确性
        R login = service.login(tuser);


        //不正确的情况
        if (login.getCode() == 0) {
            return R.error("账号密码不正确");
        }
        //正确情况,封装用户信息,存入一个session,存到redis中,设置半小时有效期

        // 如果jxcUserKey是null 生成一个
        if (UserKey == null) {
            UserKey = UUID.randomUUID().toString().replaceAll("-", "");
        }
        //存入的键值对    key  为 随机的session值      ,value为uid
        String key = StringAppend.getnewString(Constant.USERKEY, UserKey);
        TUser tt = (TUser) login.getData();
        tt.setPassword(null);
        redisService.set(new RedisDTO(key, tt, Long.valueOf(1800)));

        // 写回user-key 到cookie
        Cookie cookie = new Cookie("user-key", UserKey);
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        resp.addCookie(cookie);

        return R.ok("登录成功,即将跳转首页");
    }


    /*验证是否登录功能
     * 检查redis的登陆信息，并重新将登录验证设置半小时过期*/
    @RequestMapping("/islogin")
    @ResponseBody
    public R islogin(@CookieValue(value = "user-key", required = false) String UserKey) {
        //判断是否有cookie
        if (UserKey == null) {
            return R.error(0, "您还登录");
        }
        String key = StringAppend.getnewString(Constant.USERKEY, UserKey);
        ////有user-key的话，获取值，拼接字符串去查redis的值
        R r = redisService.get(new RedisDTO(key, Long.valueOf(1800)));
        Object data = r.getData();

        if (r.getCode() == 200) {
            return R.ok(200, "已登录成功", data);
        }

        return R.error("未登录");
    }

    /*登出功能
     * user登出*/
    @RequestMapping("/loginout")
    @ResponseBody
    public R isloginout(HttpServletResponse resp, @CookieValue(value = "user-key", required = false) String UserKey) throws IOException {
        //判断是否有cookie
        if (UserKey != null && UserKey != "") {
            String key = StringAppend.getnewString(Constant.USERKEY, UserKey);
            redisService.delect(new RedisDTO(key));

        }
        //删除本地的cookie
        //2.cookie要删除
        // 写回jxc-user-key
        Cookie cookie = new Cookie("user-key", UserKey);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        return R.ok("注销成功");

    }


    /*邮箱注册功能
     * 注册ok后发送验证短信验证登录验证*/
    @RequestMapping("/email/register")
    @ResponseBody
    public R register(TUser tuser, HttpServletResponse resp, @CookieValue(value = "user-key", required = false) String UserKey) {


        //判断是邮箱注册是否传入值

        if (tuser.getEmail() == null || tuser.getPassword() == null) {

            return R.error(0, "填写错误,请重新填写试试");
        }

        //此时邮箱已经在了,现在需要调用服务完成任务

        //1，验证邮箱是否已经存在


        //2.发送一份激活验证的邮件

        String uuid = UUID.randomUUID().toString();
        R sender = service.sender(tuser, uuid);
        if (sender.getCode() == 0) {
            return R.error("发送邮箱失败");
        }
        //3,将用户信息存入到数据库中,此时激活状态要写成fales
        tuser.setFlag(false);
        boolean save = service.save(tuser);
        if (!save) {
            return R.error("存入mysql数据库发生了错误");
        }

        //4,存入一个时常为30分的redis键值对  key为user:register:uuid;   value为1213669691@qq.com


        R set = redisService.set(new RedisDTO(StringAppend.getnewString(Constant.USERREGISTER, uuid), tuser.getEmail(), Long.valueOf(1800)));

        if (set.getCode() != 200) {
            return R.error(0, "存入redis数据库发生了错误");
        }


        //4,同时也存入一个市场为30分钟的cookie
        // 写回jxc-user-key
        Cookie cookie = new Cookie("active-key", uuid);
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        resp.addCookie(cookie);


        //5,返回前端信息让他去激活邮箱链接
        return R.ok("请激活邮箱,有效时常30分钟,逾期不候");

    }

    /*
    * 邮箱激活功能 通过restful传值进来，能得到一个uuid
    * */
    /*点击激活邮箱时候,执行的代码*/
    @RequestMapping("active/{uuid}")
    public String activeAccount(@PathVariable String uuid , @CookieValue(value = "active-key",required = false)String activeKey, Model model){
        //进入这个接口说明已经点击了链接,下面只有两种情况,1,超时,2未超时   ---超时未超时用cookie判定  (或者是redis判定 )
        R r = redisService.get(new RedisDTO(StringAppend.getnewString(Constant.USERREGISTER, uuid)));

        if(!uuid.equals(activeKey)&&r.getData()==null){

           R R1= R.error("链接已经超时");

           //删除数据库中中email的数字
            model.addAttribute("R1",R1);
            return "emailchacksuccess";
        }
        //说您有数据，需要将数据库中的这个数据值的状态改为true；
        String email= (String) r.getData();
        TUser tuser2 =new TUser();
        tuser2.setEmail(email);
        tuser2.setFlag(true);
        boolean updatebyemail = service.updatebyemail(tuser2);
        if(!updatebyemail){
            R R1 = R.error("修改数据库状态失败");
            model.addAttribute("R1",R1);
            return "emailchacksuccess";
        }
        R R1 = R.ok( "激活成功");
        model.addAttribute("R1",R1);

        return "emailchacksuccess";
    }





}
