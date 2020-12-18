package com.example.feignclient.controller;

import com.example.feignclient.feign.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {
    @Autowired
    HelloService helloService;
    @GetMapping("/hi")
    public String sayHello(){
        return helloService.sayHello();
    }
    @GetMapping("/test")
    public String test(){return helloService.selectall();}
}
