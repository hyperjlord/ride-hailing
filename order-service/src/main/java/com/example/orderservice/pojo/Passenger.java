package com.example.orderservice.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("Passenger")
@Data
public class Passenger {
    String user_id;
    String password;
    String name;
    String sex;
    Double balance;
    String ppic_url;
}
