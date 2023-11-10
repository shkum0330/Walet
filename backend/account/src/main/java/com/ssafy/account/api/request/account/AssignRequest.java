package com.ssafy.account.api.request.account;

import lombok.Data;

@Data
public class AssignRequest {

    // 양도하는 사람의 계좌 PK
    private Long transferorAccountId;

    // 양도받는 사람의 계좌 PK
    private Long transfereeAccountId;

    public AssignRequest(Long transferorAccountId, Long transfereeAccountId) {
        this.transferorAccountId = transferorAccountId;
        this.transfereeAccountId = transfereeAccountId;
    }
}
