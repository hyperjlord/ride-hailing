package com.example.orderservice.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    EurekaClientFeign eurekaClientFeign;
    public String sayHello(){
        return eurekaClientFeign.sayHelloFromOrderService();
    }
    public String selectall(){return  eurekaClientFeign.selectall();}
}
