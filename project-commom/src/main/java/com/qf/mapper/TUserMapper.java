package com.qf.mapper;

import com.qf.entity.TUser;

public interface TUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

     //通过email查询修改状态（不等于null的都会修改，不删除之前的内容）
     int updateByEmailSelective(TUser record);

    int updateByPrimaryKey(TUser record);

    //查询是否存在这个用户
    TUser checkUser(TUser record);
}