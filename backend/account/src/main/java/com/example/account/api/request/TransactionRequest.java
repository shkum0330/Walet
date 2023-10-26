package com.example.account.api.request;

import com.example.account.db.entity.Account;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    
    private Long myAccountId; // 내 계좌번호
    private Long companyAccountId; // 이체할 회사의 계좌번호
    private String transactionType; // 거래 타입
    private Long pay; // 거래 금액
}
