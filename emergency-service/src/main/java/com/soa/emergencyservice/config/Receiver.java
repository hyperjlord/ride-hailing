package com.soa.emergencyservice.config;

import com.soa.emergencyservice.dao.ContactDao;
import com.soa.emergencyservice.entity.Contact;
import com.soa.emergencyservice.vo.DriverDetailVo;
import com.soa.emergencyservice.vo.Order;
import net.minidev.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
@RabbitListener(queues = "testqueue")
public class Receiver {
    @Autowired
    ContactDao contactDao;
    @RabbitHandler
    public void process(String content) throws Exception {
        System.out.println("Receiver:" + content);
        sendMessageByUid(content);
    }
    public void sendMessageByUid(String order_id) throws Exception {
        Order order=contactDao.findOrderByOrderId(order_id);
        String user_id=order.getUser_id();
        String driver_id=order.getDriver_id();
        DriverDetailVo driverDetailVo=contactDao.findDriverDetailByDriverId(driver_id);
        List<Contact> contacts=contactDao.findContactByUserId(user_id);
        String phone_number=contacts.get(0).getEmergency_phone();

        URL url = new URL("https://sms-api.upyun.com/api/messages");
        //设置 JSON 参数
        JSONObject object = new JSONObject();
        object.put("template_id", 4053);
        object.put("mobile", phone_number);
        String vars=user_id+"|"+driverDetailVo.getName()+"|"+driverDetailVo.getId_card()+"|"+driverDetailVo.getCar_number();
        object.put("vars",vars);

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