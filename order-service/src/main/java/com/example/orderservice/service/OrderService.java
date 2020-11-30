package com.example.orderservice.service;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import org.apache.ibatis.annotations.Param;
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
        return orderMapper.selectAllOrders();
    }

    public List<Order> getMatchOrders(Double lon, Double lat) {
        return orderMapper.getMatchOrders(lon, lat);
    }

    public Order selectById(String user_id) {
        return orderMapper.selectById(user_id);
    }

    public void insertOrder(String user_id, String from_name, String to_name,
                            Double from_lon, Double from_lat, Double to_lon, Double to_lat, Date date, int passenger_num) {
        orderMapper.insertOrder(user_id, from_name, to_name, from_lon, from_lat, to_lon, to_lat, date, passenger_num);
    }
}
