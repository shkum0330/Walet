package com.ssafy.notification.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.notification.api.request.PaymentNotificationRequest;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class NotificationMessageListener {
    private final ObjectMapper objectMapper;
    private final NotificationService fcmTokenService;

    @RabbitListener(queues = "chat-queue")
    public void notice(Message message) {
        PaymentNotificationRequest paymentNotificationRequest = null;
        try{
            paymentNotificationRequest=objectMapper.readValue(message.getBody(),PaymentNotificationRequest.class);
            fcmTokenService.noticeByPaymentNotificationRequest(paymentNotificationRequest);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
