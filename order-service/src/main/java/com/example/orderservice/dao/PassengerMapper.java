package com.example.orderservice.dao;

import com.example.orderservice.pojo.Passenger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface PassengerMapper {
    Passenger findPassengerById(@Param("user_id") String user_id);
    String findPassengerFromOrder(@Param("order_id") String order_id);
    int updateBalance(Passenger passenger);
}
