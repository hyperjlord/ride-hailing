package com.soa.emergencyservice.dao;

import com.soa.emergencyservice.entity.Contact;
import com.soa.emergencyservice.vo.DriverDetailVo;
import com.soa.emergencyservice.vo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactDao {
    @Select("SELECT * FROM soadb.order WHERE order_id = #{order_id}")
    Order findOrderByOrderId(String order_id);
    @Select("SELECT * FROM soadb.driver NATURAL JOIN soadb.car WHERE driver_id = #{driver_id} ")
    DriverDetailVo findDriverDetailByDriverId(String driver_id);
    @Select("SELECT * FROM soadb.contact WHERE user_id = #{user_id}")
    List<Contact> findContactByUserId(String user_id);
}
