package com.example.notification_service.services;


import com.example.notification_service.entities.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotification(NotificationRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getToEmail());
        message.setSubject(request.getSubject());
        message.setText(request.getMessage());
        message.setFrom("bsdasma6@gmail.com");

        mailSender.send(message);
    }
}

