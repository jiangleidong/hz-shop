package com.qf.service;

import com.qf.entity.TOrder;
import com.qf.entity.TProduct;
import com.qf.mapper.TOrderMapper;
import com.qf.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-17 00:42
 **/
@Service
public class OrdertService {
    @Autowired
    private TOrderMapper tOrderMapper;

    public int getone(TOrder torder){


        return tOrderMapper.insert(torder);

    }


}
