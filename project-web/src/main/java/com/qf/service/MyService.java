package com.qf.service;


        import com.qf.service.fallback.AdminServiceHystrix;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "orderandcart-service",fallback = AdminServiceHystrix.class)
public interface MyService {

    @RequestMapping("hi/abc")
    public String sayHi(@RequestParam String message);

}
