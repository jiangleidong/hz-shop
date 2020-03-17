package com.qf.service;

import com.qf.service.fallback.AdminServiceHystrix;
import com.qf.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-15 15:57
 **/
@FeignClient(value = "orderandcart-service",fallback = AdminServiceHystrix.class)
public interface ShopCartService {




    @RequestMapping("addproduct")
    public R addProduct(@RequestParam String id,@RequestParam Long pid,@RequestParam int count);

    @RequestMapping("clean")
    public R clean(@RequestParam String id);

    @RequestMapping("update")
    public R update(@RequestParam String id,@RequestParam Long pid,@RequestParam int count);

    @RequestMapping("showcart")
    public R showCart(@RequestParam String id);

    @RequestMapping("merge")
    public R merge(@RequestParam Long id,@RequestParam String keyid);
}
