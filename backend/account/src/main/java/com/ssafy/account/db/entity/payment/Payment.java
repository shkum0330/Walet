package com.ssafy.account.db.entity.payment;

import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.common.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;


import static com.ssafy.account.common.api.status.ProcessStatus.*;
import static javax.persistence.GenerationType.IDENTITY;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name="seller_id",nullable = false)
    private Long sellerId;

    @Column(name="buyer_id")
    private Long buyerId;

    @Enumerated(EnumType.STRING)
    @Column(name="status",length = 10,nullable = false)
    private ProcessStatus status;

    @Column(name="payment_amount",nullable = false)
    private Long paymentAmount;

    @Builder
    public Payment(Long sellerId, ProcessStatus status, Long paymentAmount) {
        this.sellerId = sellerId;
        this.status = status;
        this.paymentAmount = paymentAmount;
    }

    public void setBuyerId(Long buyerId){
        log.info("{} {}",this.id,buyerId);
        this.buyerId=buyerId;
    }
    public void completePayment(){
        this.status= COMPLETE;
    }
    public void closePayment(){
        this.status= CANCEL;
    }

}
