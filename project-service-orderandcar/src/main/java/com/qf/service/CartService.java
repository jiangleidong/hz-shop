package com.qf.service;

import com.qf.vo.R;

public interface CartService {


    R addProduct(String id, Long pid, int count);

    R clean(String id);

    R update(String id, Long pid, int count);

    R showCart(String id);

    R merge(Long id, String keyid);
}
