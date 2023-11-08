package com.ssafy.account.api.request.payment;

import lombok.Data;

@Data
public class RFIDAuthRequest {
    private Long senderId;
    private String rfidCode;
}
