package com.soa.emergencyservice.vo;

import lombok.Data;

@Data
public class DriverDetailVo {
    String driver_id;
    String password;
    String name;
    String sex;
    Double score;
    String id_card;
    int drive_age;
    Double balance;
    String car_number;
    String brand;
    String model;
    String color;
}
