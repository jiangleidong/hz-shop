package com.qf.service;


import com.qf.entity.TUser;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;



    public String getone(Long id){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
        String uri = "http://user-service/user/getone?id="+id;
        return restTemplate.getForObject(uri,String.class);
    }

/*登录功能
* user登录验证,并把登录的user信息放到redis中,设置市场半小时过期*/

    public R login(TUser tuser){
        //调用服务的提供者并获得结果并返回

        //服务提供者返回的是一个String
       //String uri = "http://user-service/user/login?uname="+tuser.getUname()+"&password="+tuser.getPassword();
        //R forObject = restTemplate.getForObject(uri, R.class);

        String url = "http://user-service/user/login?";
        R r = restTemplate.postForObject(url, tuser, R.class);


        return r ;

    }

    /*注册功能
     * 邮箱验证,并把登录的邮箱地址信息放到redis中,设置半小时过期*/

    public R sender(TUser tuser,String uuid){
        //调用服务的提供者并获得结果并返回


        //@RequestMapping("send/{addr}/{uuid}")
        //服务提供者返回的是一个String


        String uri = "http://user-email/email/send/"+tuser.getEmail()+"/"+ uuid;

        R forObject = restTemplate.getForObject(uri, R.class);
        //System.out.println(forObject);

        return forObject;

    }

    /*
    * 保存用户对象数据
    * */
    public boolean save(TUser tuser) {
        String uri = "http://user-service/user/save/";
        R r= restTemplate.postForObject(uri, tuser,R.class);
        if (r.getCode()==200){
            return true;

        }
        return  false;

    }

    /*
     * 通过邮箱信息去修改用户对象数据
     * */
    public boolean updatebyemail(TUser tuser) {
        String uri = "http://user-service/user/updatebyemail/";
        R r= restTemplate.postForObject(uri, tuser,R.class);
        if (r.getCode()==200){
            return true;

        }
        return  false;

    }



    //当熔断被打开，那么此方法会被调用
    /*public String hiError(String message){
        return String.format("您的消息:%s未发送成功，请检查您的网络",message);
    }*/





}
