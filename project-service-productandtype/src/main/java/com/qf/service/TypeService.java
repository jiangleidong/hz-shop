package com.qf.service;

import com.qf.dto.ProductTypeDTO;
import com.qf.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: hz-shop
 * @description: 分类获取
 * @author: Mr.jiang
 * @create: 2020-03-13 17:59
 **/
@Service
public class TypeService {
    @Autowired
    private TProductTypeMapper mapper;


    public List<ProductTypeDTO> getMapper(long pid) {


        return mapper.selectByPid(pid);
    }
}
