package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
public class OrderController {
    class Test{
        public String hello="hello world";
    }
    @Autowired
    public OrderService orderService;
    //@GetMapping(value="/hello",produces = "application/json;charset=UTF-8")
    @GetMapping("/hello")
    public Test helloworld(){
        Test test=new Test();
        return test;
    }
    @GetMapping("/selectall")
    public List<Order> selectall(){
        return orderService.selectAll();
    }
    @GetMapping("/selectbyid")
    public Order selectById(@RequestParam("user_id") String user_id){
        return orderService.selectById(user_id);
    }

    //返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序
    @GetMapping("/getmatchorders")
    public List<Order> getMatchOrders(@RequestParam("lon") Double longitude,@RequestParam("lat") Double latitude){
        return  orderService.getMatchOrders(longitude,latitude);
    }

}
