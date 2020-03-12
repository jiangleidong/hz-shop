package com.qf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qf.constant.Constant;
import com.qf.dto.RedisDTO;
import com.qf.entity.TUser;
import com.qf.service.RedisService;
import com.qf.util.StringAppend;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class MyZuulFilter extends ZuulFilter {

    @Autowired
    private RedisService redisService;

    /*
    配置过滤器类型，根据生命周期的不同，有四种类型
    1.pre: 路由之前
    2.routing: 路由之时
    3.post:   路由之后
    4.error:  在上面三者执行过程中出现了异常就会调用该error过滤器
     */
    @Override
    public String filterType() {
        return "pre";
    }

    //相同过滤器类型之间确定执行的顺序，0就表示第一个执行。
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    配置是否需要过滤：true 需要   false  不需要
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器要执行过滤的具体的内容
     * 如果这一次请求，没有携带cookie，那么就不进行路由。
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //得到request的当前的上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        //得到request对象
        HttpServletRequest request = ctx.getRequest();
        Cookie[] cookies = request.getCookies();

        //进行路由
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.USERKEY.equals(cookie.getName())) {
                    String uuid = cookie.getValue();
                    String key = StringAppend.getnewString(Constant.USERKEY, uuid);
                    R r = redisService.get(new RedisDTO(key, Long.valueOf(1800)));
                    Object data = r.getData();
                    if (data != null) {
                        //用户已登录
                        TUser tUser = (TUser) data;
                        //存入到request域中
                        request.setAttribute("user", tUser);
                        return true;

                    }

                }
            }
        }
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        ctx.set("isSuccess", true);

        return null;
    }
}

