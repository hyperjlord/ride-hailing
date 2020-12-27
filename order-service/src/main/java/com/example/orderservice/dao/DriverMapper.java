package com.example.orderservice.dao;

import com.example.orderservice.pojo.Driver;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface DriverMapper {
    Driver findDriverById(String driver_id);
    String findDriverFromOrder(String order_id);
    int updateBalance(Driver driver);
}
