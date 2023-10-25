package com.ssafy.notification.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.notification.api.request.PaymentNotificationRequest;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationMessageListener {
    private final ObjectMapper objectMapper;
    private final NotificationService fcmTokenService;

    @RabbitListener(queues = "payment-queue")
    public void paymentNotice(Message message) {
        PaymentNotificationRequest paymentNotificationRequest = null;
        try{
            log.info("message: {}",new String(message.getBody()));
            paymentNotificationRequest=objectMapper.readValue(message.getBody(),PaymentNotificationRequest.class);
            log.info("paymentNotificationRequest: {}",paymentNotificationRequest);
            fcmTokenService.noticeByPaymentNotificationRequest(paymentNotificationRequest);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

}
