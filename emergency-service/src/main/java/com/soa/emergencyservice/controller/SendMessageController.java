package com.soa.emergencyservice.controller;

import com.soa.emergencyservice.config.Sender;
import com.soa.emergencyservice.dao.ContactDao;
import com.soa.emergencyservice.entity.Contact;
import com.soa.emergencyservice.vo.DriverDetailVo;
import com.soa.emergencyservice.vo.Order;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
public class SendMessageController {
    @Autowired
    ContactDao contactDao;
    @Autowired
    Sender sender;
    @ApiOperation(value = "给紧急联系人发短信",notes="根据用户id，给紧急联系人发送一条短信")
    @GetMapping("/emergency/message")
    public void sendMessage(@RequestParam("order_id") String order_id){
        sender.send(order_id);
    }
}
