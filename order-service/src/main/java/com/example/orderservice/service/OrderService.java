package com.example.orderservice.service;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.qo.SetOrderQO;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    public List<Order> selectAll() {
        return orderMapper.findAllOrders();
    }

    public List<Order> getMatchOrders(Double lon, Double lat) {
        return orderMapper.findMatchOrders(lon, lat);
    }

    public Order selectById(String user_id) {
        return orderMapper.findById(user_id);
    }

    public void setNewOrder(SetOrderQO setOrderQO) {
        Order order=new Order();
        order.setOrder_id(setOrderQO.getOrder_id());
        order.setUser_id(setOrderQO.getUser_id());
        order.setType(setOrderQO.getType());
        order.setDatetime(setOrderQO.getDatetime());
        order.setFrom_name(setOrderQO.getFrom_name());
        order.setTo_name(setOrderQO.getTo_name());
        order.setFrom_lon(setOrderQO.getFrom_lon());
        order.setFrom_lat(setOrderQO.getFrom_lat());
        order.setTo_lon(setOrderQO.getTo_lon());
        order.setTo_lat(setOrderQO.getTo_lat());
        order.setDescription(setOrderQO.getDescription());
        order.setState(0);
        order.setDriver_id(null);
        orderMapper.saveOrder(order);
   }
}
