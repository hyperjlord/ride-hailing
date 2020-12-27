package com.example.orderservice.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("Contact")
@Data
public class Contact {
    String user_id;
    String emergency_phone;
}
