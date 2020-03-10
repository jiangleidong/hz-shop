package com.qf.service;

import com.qf.entity.TUser;

/**
 * @program: hz-shop
 * @description: user 的service层接口
 * @author: Mr.jiang
 * @create: 2020-03-09 21:31
 **/
public interface IUserService {
    TUser selectbyid(long id);

    //查询是否存在这个用户
    TUser checkUser(TUser record);

    /*存入用户的信息*/
    int insert(TUser record);

}
