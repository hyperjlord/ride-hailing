package com.example.orderservice.dto;

import lombok.Data;

@Data
public class DriverDetailDto {
    public String driver_id;
    public String password;
    public String name;
    public String sex;
    public Double score;
    public String id_card;
    public int drive_age;
    public Double balance;
    String car_number;
    String brand;
    String model;
    String color;
}
