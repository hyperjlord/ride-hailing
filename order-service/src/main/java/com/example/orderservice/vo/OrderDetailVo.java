package com.example.orderservice.vo;

import com.example.orderservice.pojo.Car;
import com.example.orderservice.pojo.Comment;
import com.example.orderservice.pojo.Passenger;
import com.example.orderservice.dto.DriverDetailDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
@Data
public class OrderDetailVo {
    public String order_id;
    public int type;
    public int state;
    public Passenger passenger;
    public int passenger_num;
    public Double price;
    public Date datetime;
    public String from_name;
    public String to_name;
    public Double from_lat;
    public Double from_lon;
    public Double to_lat;
    public Double to_lon;
    public DriverDetailDto driver;
    public String description;
    public Comment comment;
    public Date taken_time;
}
