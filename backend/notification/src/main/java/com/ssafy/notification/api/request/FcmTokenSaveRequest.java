package com.ssafy.notification.api.request;

import lombok.Data;

@Data
public class FcmTokenSaveRequest {
    Long memberId;
    String fcmToken;
}
