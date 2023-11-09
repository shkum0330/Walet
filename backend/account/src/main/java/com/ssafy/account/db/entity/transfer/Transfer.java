package com.ssafy.account.db.entity.transfer;

import com.ssafy.account.common.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import static com.ssafy.account.db.entity.transfer.Transfer.TransferStatus.*;
import static javax.persistence.GenerationType.IDENTITY;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer extends BaseTimeEntity {
    public enum TransferStatus {
        PENDING, // 대기 중
        COMPLETE, // 완료
        CANCEL // 취소
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @Column(name="transferor_id")
    private Long transferorId; // 원래 계좌 주인의 id

    @Column(name="transferee_id")
    private Long transfereeId; // 새로운 계좌 주인의 id

    @Column(name="content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name="status",length = 10,nullable = false)
    private TransferStatus status;

    public Transfer(String content, TransferStatus status) {
        this.content=content;
        this.status = PENDING;
    }


    public void setTransferorId(Long transferorId) {
        this.transferorId = transferorId;
    }


    public void setTransfereeId(Long transfereeId) {
        this.transfereeId = transfereeId;
    }

    public void completeTransfer(){
        this.status = COMPLETE;
    }
    public void closeTransfer(){
        this.status = CANCEL;
    }

}
