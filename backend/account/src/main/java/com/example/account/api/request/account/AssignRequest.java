package com.example.account.api.request.account;

import lombok.Data;

@Data
public class AssignRequest {

    // 양도하는 사람의 계좌 PK
    private Long transfererAccountId;

    // 양도받는 사람의 계좌 PK
    private Long transfereeAccountId;
}
