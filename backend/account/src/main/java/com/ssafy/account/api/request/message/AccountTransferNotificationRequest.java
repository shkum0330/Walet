package com.ssafy.account.api.request.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AccountTransferNotificationRequest {
    Long receiveMemberId;
    private String ownerName;

    public AccountTransferNotificationRequest(Long receiveMemberId, String ownerName) {
        this.receiveMemberId = receiveMemberId;
        this.ownerName = ownerName;
    }
}

