package com.example.feignclient.controller;

import com.example.feignclient.feign.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;
    @GetMapping("/hi")
    public String sayHello(){
        return helloService.sayHello();
    }
}
