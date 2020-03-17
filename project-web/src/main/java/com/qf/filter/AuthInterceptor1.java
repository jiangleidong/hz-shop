//package com.qf.filter;
//
//
//import com.qf.constant.Constant;
//import com.qf.entity.TUser;
//
//import com.qf.util.StringAppend;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class AuthInterceptor1 implements HandlerInterceptor {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//
//        //1.获取cookie中uuid
//
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null){
//            for (Cookie cookie : cookies) {
//
//
//                if("user-key".equals(cookie.getName())){
//                    String UserKey = cookie.getValue();
//                    String key = StringAppend.getnewString(Constant.USERKEY, UserKey);
//                    Object o = redisTemplate.opsForValue().get(key);
//                    if(o!=null){
//                        //用户已登录
//                        TUser tUser = (TUser) o;
//                        //存入到request域中
//                        request.setAttribute("user",tUser);
//                        return true;
//
//                    }
//
//                }
//            }
//        }
//
//        return true;
//    }
//
//}
