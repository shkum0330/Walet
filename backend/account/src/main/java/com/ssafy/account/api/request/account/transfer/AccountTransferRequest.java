package com.ssafy.account.api.request.account.transfer;

import lombok.Data;

@Data
public class AccountTransferRequest {
    private String newOwner;
    private String accountNumber;
    private String content;

}
