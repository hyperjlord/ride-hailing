package com.example.orderservice.dao;

import com.example.orderservice.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from order")
    @Results({
            @Result(property = "user_id",column = "用户id")
    })
    List<Order> orders();
}
