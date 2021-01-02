package com.example.orderservice.dao;

import com.example.orderservice.pojo.Driver;
import com.example.orderservice.vo.DriverLocationVO;
import com.example.orderservice.vo.DriverNearbyVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
public interface DriverMapper {
    Driver findDriverById(String driver_id);
    String findDriverFromOrder(String order_id);
    int updateBalance(Driver driver);
    int insertLocation(String driver_id);
    int updateLocation(String driver_id, Double lon, Double lat, Date update_time);
    DriverLocationVO findLocationById(String driver_id);
    List<DriverNearbyVo> findNearestDriver(Double lon, Double lat);
}
