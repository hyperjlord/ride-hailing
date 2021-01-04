package com.soa.emergencyservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(String order_id){
        String content = order_id;
        System.out.println("Sender:" +content);
        this.rabbitmqTemplate.convertAndSend("testqueue", content);
    }
}