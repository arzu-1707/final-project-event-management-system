package com.arzuahmed.ticketingsystem.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
       message.setFrom("arzuahmedova.1707@gmail.com");
       message.setTo(to);
       message.setSubject(subject);
       message.setText(body);
       javaMailSender.send(message);
    }
}
