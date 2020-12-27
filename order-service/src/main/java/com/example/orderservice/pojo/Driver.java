package com.example.orderservice.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("Driver")
@Data
public class Driver {
    public String driver_id;
    public String password;
    public String name;
    public String sex;
    public Double score;
    public String id_card;
    public int drive_age;
    public Double balance;
}
