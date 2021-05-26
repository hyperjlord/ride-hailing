package com.example.orderservice.dao;

import com.example.orderservice.pojo.*;
import com.example.orderservice.vo.OrderDetailVo;
import com.example.orderservice.dto.DriverDetailDto;
import com.example.orderservice.vo.OrderWithDistanceVO;
import org.apache.ibatis.annotations.*;


import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
   //查找并返回所有订单详情
   List<OrderDetailVo> findAllDetail();

   List<OrderDetailVo> findAllOrderDetailByUidAndState(String user_id,int state);

   List<OrderDetailVo> findAllOrderDetailByDidAndState(String driver_id,int state);

    /**
     *
     * @param order_id 订单id
     * @return 对应的订单详情
     */
   OrderDetailVo findOrderDetailByOrderId(String order_id, int type);

    /**
     *
     * @param user_id 用户id
     * @param type 订单种类
     * @return 订单详情信息
     */
   List<OrderDetailVo> findOrderDetailByUserId(String user_id,int type);

    /**
     *
     * @param user_id 用户id
     * @param type 订单种类
     * @param state 订单状态
     * @return 订单详情
     */
   List<OrderDetailVo> findOrderDetailByUserIdAndState(String user_id,int type,int state);

   List<OrderDetailVo> findOrderDetailByDriverIdAndState(String driver_id,int type,int state);

   List<OrderWithDistanceVO> findNearestOrders(Double lon,Double lat,Date current_time);

   List<OrderWithDistanceVO> findMatchOrders(Double from_lon,Double from_lat,Double to_lon, Double to_lat,int type,Date datetime);

   //根据订单id查找对应的评论
   Comment findCommentById(@Param("order_id") String order_id);

   //根据用户id查找用户
   Passenger findPassengerById(@Param("user_id") String user_id);

   //根据司机id返回司机
   Driver findDriverById(@Param("driver_id") String driver_id);

   //根据司机id返回对应车辆的全部信息
   Car findCarById(@Param("driver_id") String driver_id);

   //根据司机id返回司机全部详细信息（包括司机的车辆信息）
   DriverDetailDto findDriverDetailById(@Param("driver_id") String driver_id);

   //更新订单状态
   int takeOrder(@Param("order_id")String order_id, @Param("driver_id") String driver_id, Date taken_time);

   int pickUp(@Param("order_id")String order_id);

   int finishOrder(@Param("order_id")String order_id);

   int saveComment(@Param("order_id")String order_id,@Param("score") Double score,@Param("content") String content);

   void deleteByOid(String order_id,int type);

    @Select("select * from ridehailing.order")
    List<Order> findAllOrders();

    @Select("select * from ridehailing.order where order_id = #{order_id}")
    Order findOrderById(String order_id);

    @Insert("INSERT INTO ridehailing.order(order_id,type,state,user_id,passenger_num,price,datetime," +
            "from_name,to_name,from_lon,from_lat,to_lon,to_lat,driver_id,description) " +
            "VALUES (#{order_id},#{type},#{state},#{user_id},#{passenger_num},#{price},#{datetime}," +
            "#{from_name},#{to_name},#{from_lon},#{from_lat},#{to_lon},#{to_lat},#{driver_id},#{description})")
    void saveOrder(Order order);
}
