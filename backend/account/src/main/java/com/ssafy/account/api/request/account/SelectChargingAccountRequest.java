package com.ssafy.account.api.request.account;

import lombok.Data;

@Data
public class SelectChargingAccountRequest {
    Long myAccountId;
    Long chargingAccountId;
}
