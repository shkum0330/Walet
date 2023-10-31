package com.example.account.api.request;

import lombok.Data;

@Data
public class SelectChargingAccountRequest {
    Long myAccountId;
    Long chargingAccountId;
}
