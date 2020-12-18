package com.example.orderservice.dao;

import com.example.orderservice.pojo.Passenger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PassengerMapper {
    @Select("select * from passenger where user_id = #{user_id}")
    Passenger findPassengerById(String user_id);
}
