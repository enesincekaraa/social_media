package com.enesincekara.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("eynesil.belediyesi@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        message.setSentDate(new java.util.Date());
        mailSender.send(message);
    }
}
