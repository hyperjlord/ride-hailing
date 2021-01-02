package com.soa.emergencyservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private static Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendSimpleMail(String toMail, String subject,  String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            sender.send(simpleMailMessage);
            logger.info("发送给:{} 邮件已经发送。 subject:{}", toMail, subject);
        } catch (Exception e) {
            logger.info("发送给:{} send mail error subject:{}", toMail, subject);
            e.printStackTrace();
        }
    }
}
