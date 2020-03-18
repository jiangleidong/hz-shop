package com.qf.mapper;

import com.qf.entity.TOrderdetail;

import java.util.List;

public interface TOrderdetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderdetail record);

    int insertSelective(TOrderdetail record);

    TOrderdetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderdetail record);

    int updateByPrimaryKey(TOrderdetail record);

    public int savelist(List<TOrderdetail> tds);
}