package com.example.orderservice.pojo;



import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class Order{
    public Long order_id;
    public int type;
    public int state;
    public String user_id;
    public int passenger_num;
    public Date datetime;
    public String from_name;
    public String to_name;
    public Double from_lat;
    public Double from_lon;
    public Double to_lat;
    public Double to_lon;
    public String driver_id;
    public String description;
}
