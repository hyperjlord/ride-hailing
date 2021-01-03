package com.example.orderservice.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class SetOrderQO {
    public String user_id;
    public int type;
    public int passenger_num;
    public Double price;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    public Date datetime;
    public String from_name;
    public String to_name;
    public Double from_lat;
    public Double from_lon;
    public Double to_lat;
    public Double to_lon;
    public String description;
}
