package com.example.feignclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value="order-service",configuration = FeignConfig.class)
public interface EurekaClientFeign {
    @GetMapping("/hello")
    String sayHelloFromOrderService();
}
