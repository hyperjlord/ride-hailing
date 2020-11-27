package com.example.orderservice.pojo;



import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class Order{
    public String user_id;
    public String from_name;
    public String to_name;
    public Double from_lat;
    public Double from_lon;
    public Double to_lat;
    public Double to_lon;
    public int passenger_num;
    public Date date;
    public List<Passenger> passengers;
}
