package com.ssafy.account.service;


import com.ssafy.account.api.request.message.PaymentNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MessageSenderService {

    private static final String EXCHANGE_NAME = "app-exchange";
    private static final String ROUTING_KEY = "app-id.*";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPaymentMessage(PaymentNotificationRequest request){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,request);
    }
}
