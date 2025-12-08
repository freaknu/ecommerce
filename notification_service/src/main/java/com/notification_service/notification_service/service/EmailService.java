package com.notification_service.notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    private Logger logger = LoggerFactory.getLogger(Logger.class);
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String email,String subject,String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Ecommerce");
        message.setSubject(subject);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("Email Sent successfully");
    }
}
