package com.example.orderservice.vo;

import com.example.orderservice.pojo.Driver;
import lombok.Data;

import java.util.Date;

@Data
public class DriverLocationVO {
    Driver driver;
    Double lon;
    Double lat;
    Date update_time;
}
