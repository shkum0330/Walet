package com.ssafy.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ssafy.notification.api.request.PaymentNotificationRequest;
import com.ssafy.notification.db.entity.FcmToken;
import com.ssafy.notification.db.repository.FcmTokenRepository;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final FcmTokenRepository fcmTokenRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void save(FcmToken fcmToken) {
        fcmTokenRepository.save(fcmToken);
    }

    @Override
    public void noticeByPaymentNotificationRequest(PaymentNotificationRequest paymentNotificationRequest) {
        fcmTokenRepository.findByMemberId(paymentNotificationRequest.getReceiveMemberId()).ifPresent(
                (fcmToken) -> {
                    Notification notification= Notification
                            .builder()
                            .setTitle(paymentNotificationRequest.getSender())
                            .setBody(paymentNotificationRequest.getContent())
                            .setImage(null)
                            .build();

                    Message message=Message
                            .builder()
                            .setToken(fcmToken.getToken())
                            .setNotification(notification)
                            .build();
                    try {
                        firebaseMessaging.send(message);
                    } catch (FirebaseMessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
