package com.example.orderservice.vo;

import com.example.orderservice.pojo.Comment;
import lombok.Data;

import java.util.Date;
@Data
public class OrderDetailVo {
    public String order_id;
    public int type;
    public int state;
    public String user_id;
    public int passenger_num;
    public Double price;
    public Date datetime;
    public String from_name;
    public String to_name;
    public Double from_lat;
    public Double from_lon;
    public Double to_lat;
    public Double to_lon;
    public String driver_id;
    public String description;
    public Comment comment;
}
