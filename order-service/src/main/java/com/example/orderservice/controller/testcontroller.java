package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
public class testcontroller {
    class Test{
        public String hello="hello world";
    }
    OrderMapper orderMapper;
    //@GetMapping(value="/hello",produces = "application/json;charset=UTF-8")
    @GetMapping("/hello")
    public Test helloworld(){
        Test test=new Test();
        return test;
    }
    @GetMapping("/selectall")
    public List<Order> selectall(){
        return  orderMapper.orders();
    }
}
