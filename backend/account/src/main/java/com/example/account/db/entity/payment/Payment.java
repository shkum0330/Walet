package com.example.account.db.entity.payment;

import com.example.account.common.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {
    public enum PaymentStatus {
        PENDING,   // 대기 중
        COMPLETE,  // 완료
        CANCEL // 취소
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name="member_id",nullable = false)
    private Long sellerId;

    @Enumerated(EnumType.STRING)
    @Column(name="status",length = 10,nullable = false)
    private PaymentStatus status;

    @Column(name="payment_amount",nullable = false)
    private Long paymentAmount;

    @Builder
    public Payment(Long sellerId, PaymentStatus status, Long paymentAmount) {
        this.sellerId = sellerId;
        this.status = status;
        this.paymentAmount = paymentAmount;
    }

    public void completePayment(){
        this.status=PaymentStatus.COMPLETE;
    }
    public void closePayment(){
        this.status=PaymentStatus.CANCEL;
    }

}
