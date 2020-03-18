package com.qf.service;

import com.qf.entity.TOrderdetail;
import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-17 00:34
 **/
@Service
public class ProductService  {

    @Autowired
    private TProductMapper productMapper;

    public TProduct getone(Long pid){


        return productMapper.selectByPrimaryKey(pid);

    }



}
