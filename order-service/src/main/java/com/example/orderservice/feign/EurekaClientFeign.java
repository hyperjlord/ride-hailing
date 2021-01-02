package com.example.orderservice.feign;

import com.example.orderservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value="order-service",configuration = FeignConfig.class)
public interface EurekaClientFeign {
    @GetMapping("/hello")
    String sayHelloFromOrderService();
    @GetMapping("/selectall")
    String selectall();
}
