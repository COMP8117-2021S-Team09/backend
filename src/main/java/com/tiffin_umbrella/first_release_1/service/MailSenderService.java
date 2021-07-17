package com.tiffin_umbrella.first_release_1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MailSenderService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage mailMessage = new SimpleMailMessage();

    @Async
    public void sendRegisterEmail(final String email) {
        mailMessage.setTo(email);
        mailMessage.setSubject("Welcome to Tiffin Umbrella");
        mailMessage.setText("Registered Successfully");
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendSummaryEmail(final String email, final String content) {
        mailMessage.setTo(email);
        mailMessage.setSubject("Order Received");
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);
    }
}
