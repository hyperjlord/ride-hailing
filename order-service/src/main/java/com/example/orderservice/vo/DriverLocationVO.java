package com.example.orderservice.vo;

import com.example.orderservice.pojo.Driver;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DriverLocationVO {
    Driver driver;
    Double lon;
    Double lat;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date update_time;
}
