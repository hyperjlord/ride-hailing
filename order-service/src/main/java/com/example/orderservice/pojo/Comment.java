package com.example.orderservice.pojo;

import lombok.Data;

@Data
public class Comment {
    String order_id;
    Double score;
    String content;
}
