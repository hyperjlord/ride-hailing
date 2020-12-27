package com.example.orderservice.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("Comment")
@Data
public class Comment {
    String order_id;
    Double score;
    String content;
}
