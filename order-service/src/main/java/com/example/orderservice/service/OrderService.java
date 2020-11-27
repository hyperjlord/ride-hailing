package com.example.orderservice.service;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    public List<Order> selectAll(){
        return orderMapper.selectAllOrders();
    }

    public List<Order> getMatchOrders(Double lon,Double lat){
        return orderMapper.getMatchOrders(lon,lat);
    }

    public Order selectById(String user_id){
        return orderMapper.selectById(user_id);
    }
}
