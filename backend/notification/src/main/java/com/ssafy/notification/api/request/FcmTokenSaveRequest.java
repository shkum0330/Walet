package com.ssafy.notification.api.request;

import lombok.Data;

@Data
public class FcmTokenSaveRequest {
    Long memberId;
    String fcmToken;

    public FcmTokenSaveRequest(Long memberId, String fcmToken) {
        this.memberId = memberId;
        this.fcmToken = fcmToken;
    }
}
