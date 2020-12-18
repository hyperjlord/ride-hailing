package com.example.orderservice.dao;

import com.example.orderservice.pojo.Order;
import com.example.orderservice.pojo.Passenger;
import org.apache.ibatis.annotations.*;


import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

   /* @Results(value = {
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "passengers", column = "user_id",
                    many = @Many(select="findPassengers")),
    })*/
    @Select("select * from soadb.order")
    List<Order> findAllOrders();

    @Select("select * from soadb.order where user_id = #{user_id}")
    Order findById(String user_id);

    @Select("SELECT" +
            "*, "+
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
            "FROM soadb.order " +
            "ORDER BY distance ASC " +
            "LIMIT 20;")
    List<Order> findMatchOrders(@Param("lon") Double lon,@Param("lat") Double lat);

    @Insert("INSERT INTO soadb.order(order_id,type,state,user_id,passenger_num,datetime," +
            "from_name,to_name,from_lon,from_lat,to_lon,to_lat,driver_id,description) " +
            "VALUES (#{order_id},#{type},#{state},#{user_id},#{passenger_num},#{datetime}," +
            "#{from_name},#{to_name},#{from_lon},#{from_lat},#{to_lon},#{to_lat},#{driver_id},#{description})")
    void saveOrder(Order order);
}
