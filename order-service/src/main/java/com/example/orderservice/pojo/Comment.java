package com.example.orderservice.pojo;

import lombok.Data;

@Data
public class Comment {
    String order_id;
    int type;
    Double score;
    String content;
}
