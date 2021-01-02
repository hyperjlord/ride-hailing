package com.soa.emergencyservice.controller;

import com.soa.emergencyservice.entity.Contact;
import com.soa.emergencyservice.entity.RspResult;
import com.soa.emergencyservice.service.ContactService;
import com.soa.emergencyservice.service.SendEmailService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 调用: /email/simpleEmail
 */
@RestController
@RequestMapping("/email")
public class SendEmailController {

    private static Logger logger = LoggerFactory.getLogger(SendEmailController.class);

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private ContactService contactService;

    @ApiOperation(value = "乘客使用紧急联系服务", notes = "乘客点击按钮使用紧急联系服务")
    @RequestMapping(value = "/simpleEmail", method = {RequestMethod.GET})
    public RspResult sendSimpleEmail(HttpServletRequest request) {

        RspResult rspResult = new RspResult();
        List<Contact> contactList = contactService.findAll();
        System.out.println(contactList.size());

        contactList.forEach(contact -> {
            sendEmailService.sendSimpleMail(contact.getToEmail(), "紧急联系人通知", "向您紧急求助");
        });

        rspResult.setStatus(200);
        rspResult.setMsg("调用成功");
        return rspResult;
    }
}
