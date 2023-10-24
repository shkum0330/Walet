package com.ssafy.notification.service;

import com.ssafy.notification.api.request.PaymentNotificationRequest;
import com.ssafy.notification.db.entity.FcmToken;

public interface NotificationService {
    void save(FcmToken fcmToken);
    void noticeByPaymentNotificationRequest(PaymentNotificationRequest paymentNotificationRequest);
}
