package com.soa.securityservice.controller;

import com.soa.securityservice.pojo.Contact;
import com.soa.securityservice.pojo.RspResult;
import com.soa.securityservice.service.ContactService;
import com.soa.securityservice.service.SendEmailService;
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
