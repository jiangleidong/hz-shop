package com.qf.service.fallback;


import com.qf.service.MyService;
import com.qf.service.ShopCartService;
import com.qf.vo.R;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceHystrix implements ShopCartService {

    @Override
    public R addProduct(String id, Long pid, int count) {
        return R.error(String.format("您的网络有问题！！！"));
    }

    @Override
    public R clean(String id) {
        return R.error(String.format("您的网络有问题！！！"));
    }

    @Override
    public R update(String id, Long pid, int count) {
        return R.error(String.format("您的网络有问题！！！"));
    }

    @Override
    public R showCart(String id) {
        return R.error(String.format("您的网络有问题！！！"));
    }

    @Override
    public R merge(Long id, String keyid) {
        return R.error(String.format("您的网络有问题！！！"));
    }
}
