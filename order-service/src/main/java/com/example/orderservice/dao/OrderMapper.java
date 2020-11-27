package com.example.orderservice.dao;

import com.example.orderservice.pojo.Order;
import com.example.orderservice.pojo.Passenger;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from user_order")
    @Results(value = {
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "passengers", column = "user_id",
                    many = @Many(select="findPassengers")),
    })
    List<Order> selectAllOrders();

    @Select("select * from user_order where user_id = #{user_id}")
    Order selectById(String user_id);

    @Select("SELECT" +
            "    user_id ," +
            "    from_name ," +
            "    to_name ," +
            "    from_lon ," +
            "    from_lat ," +
            "    to_lon ," +
            "    to_lat ," +
            "    passenger_num ," +
            "    date ," +
            "    ROUND(" +
            "        6378.138 * 2 * ASIN(" +
            "            SQRT(" +
            "                POW(" +
            "                    SIN( ( #{lat} * PI() / 180 - to_lat * PI() / 180 ) / 2 ) , 2 )" +
            "                +" +
            "                COS( #{lat} * PI( ) / 180 ) * COS( to_lat * PI( ) / 180 )" +
            "                * POW( SIN( ( #{lon} * PI() / 180 - to_lon * PI() / 180 ) / 2 ) , 2 )" +
            "            )" +
            "        ) * 1000" +
            "    ) AS distance " +
            "FROM user_order " +
            "ORDER BY distance ASC " +
            "LIMIT 20;")
    @Results(value = {
            @Result(property = "passengers", column = "user_id",
                    many = @Many(select="findPassengers")),
    })
    List<Order> getMatchOrders(@Param("lon") Double lon,@Param("lat") Double lat);

    @Select("select * from passengers where user_id = #{user_id}")
    Passenger findPassengers(String user_id);
}
