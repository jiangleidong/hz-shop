package com.qf.service.impl;

import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hz-shop
 * @description: userservice接口的实现类
 * @author: Mr.jiang
 * @create: 2020-03-09 21:33
 **/
@Service
public class UserServiceImpl implements IUserService {

       @Autowired
   private TUserMapper mapper;
    @Override
    public TUser selectbyid(long id) {
        TUser tUser = mapper.selectByPrimaryKey(id);


        return tUser;
    }

    @Override
    public TUser checkUser(TUser record) {

        TUser tUser = mapper.checkUser(record);
        return tUser;
    }

    @Override
    public int insert(TUser record) {
     return mapper.insert(record);
    }

    @Override
    public int updateByEmailSelective(TUser record) {
        return mapper.updateByEmailSelective(record);
    }

    /*存入用户的信息*/

}
