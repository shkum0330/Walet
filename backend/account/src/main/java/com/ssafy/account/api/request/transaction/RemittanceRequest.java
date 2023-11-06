package com.ssafy.account.api.request.transaction;

import lombok.Data;

@Data
public class RemittanceRequest {
    private Long myAccountId;
    private Long receiverAccountId;
    private String password;
    private Long RemittanceAmount;
}
