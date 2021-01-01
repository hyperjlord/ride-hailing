package com.example.orderservice.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("Car")
@Data
public class Car {
    String driver_id;
    String car_number;
    String brand;
    String model;
    String color;
    String cpic_url;
}
