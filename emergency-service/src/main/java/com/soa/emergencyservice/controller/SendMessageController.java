package com.soa.emergencyservice.controller;

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
    @ApiOperation(value = "给紧急联系人发短信",notes="根据用户id，给紧急联系人发送一条")
    @GetMapping("/emergency/message")
    public void sendMessage(@RequestParam("order_id") String order_id){

    }
    @Component
    public class Sender {

        @Autowired
        private AmqpTemplate rabbitmqTemplate;

        public void send(String order_id){
            String content = order_id;
            System.out.println("Sender:" +content);
            this.rabbitmqTemplate.convertAndSend("hello", content);
        }
    }

    public void sendMessageByUid(String order_id) throws Exception {
        Order order=contactDao.findOrderByOrderId(order_id);
        String user_id=order.getUser_id();
        String driver_id=order.getDriver_id();
        DriverDetailVo driverDetailVo=contactDao.findDriverDetailByDriverId(driver_id);
        List<Contact> contacts=contactDao.findContactByUserId(user_id);




        URL url = new URL("https://sms-api.upyun.com/api/messages");
        //设置 JSON 参数
        JSONObject object = new JSONObject();
        object.put("template_id", 1);
        object.put("mobile", "135xxxxxxxx");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置必要参数
        conn.setConnectTimeout(10000);
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Authorization", "4PIAMFgUKcuMRqJfrEyOcMHW0bTfjn");
        conn.setRequestProperty("Content-type", "application/json");
        // 创建链接
        conn.connect();
        OutputStream os = conn.getOutputStream();
        os.write(object.toString().getBytes("UTF-8"));
        //Gets the status code from an HTTP response message
        int code = conn.getResponseCode();
        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(reader);
        char[] chars = new char[1024];
        int length = 0;
        StringBuilder result = new StringBuilder();
        while ((length = br.read(chars)) != -1) {
            result.append(chars, 0, length);
        }
        System.out.println("code:" + code + "::" + result.toString());
    }
}
