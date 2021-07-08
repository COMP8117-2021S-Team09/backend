package com.tiffin_umbrella.first_release_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderService {
    @Autowired
    JavaMailSender javaMailSender;
    public void send_Register_Email(String email){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Welcome to Tiffin Umbrella");
        msg.setText("Registered Successfully");
        javaMailSender.send(msg);
    }
    public void send_Summary_Email(String email, String content)
    {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Order Recieved");
        msg.setText(content);
        javaMailSender.send(msg);
    }
}
