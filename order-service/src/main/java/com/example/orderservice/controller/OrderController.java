package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "hello world",notes = "测试用的helloworld")
    @GetMapping("/hello")
    public Test helloworld(){
        Test test=new Test();
        return test;
    }
    @ApiOperation(value = "查询所有订单",notes="返回数据库中所有的订单信息")
    @GetMapping("/selectall")
    public List<Order> selectall(){
        return orderService.selectAll();
    }

    @ApiOperation(value = "根据用户id返回订单",notes = "根据用户id返回订单")
    @GetMapping("/selectbyid")
    public Order selectById(@ApiParam(value="用户id") @RequestParam("user_id") String user_id){
        return orderService.selectById(user_id);
    }

    //返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序
    @ApiOperation(value = "查询乘客目的地与司机的目的地最近的订单",notes = "返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序")
    @GetMapping("/getmatchorders")
    public List<Order> getMatchOrders(@ApiParam(value ="司机所在的经度" ) @RequestParam("lon") Double longitude,
                                      @ApiParam(value ="司机所在的纬度" ) @RequestParam("lat") Double latitude){
        return  orderService.getMatchOrders(longitude,latitude);
    }

}
