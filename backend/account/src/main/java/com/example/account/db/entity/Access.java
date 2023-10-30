package com.example.account.db.entity;

import com.example.account.api.request.AccessRequest;
import com.example.account.common.domain.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Access extends BaseTimeEntity { // 계좌 접근 신청 및 권한 여부 확인

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long id;
    @Column(name = "animal_name", nullable = false)
    private String animalName; // 상대방 반려동물 이름
    @Column(name = "content", nullable = false)
    private String content; // 상대방에게 보낼 메시지
    @Column(name = "random_member_id", nullable = false)
    private String randomMemberId; // 접근요청한 사람의 난수회원ID
    @Column(name = "account_number", nullable = false)
    private String accountNumber; // 접근신청할 계좌번호
    @Column(name = "is_confirmed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isConfirmed; // 수락여부 -> 수락이 됐으면 권한이 생성된 것

    public Access(AccessRequest request) {
        this.animalName = request.getAnimalName();
        this.content = request.getContent();
        this.randomMemberId = request.getRandomMemberId();
        this.accountNumber = request.getAccountNumber();
    }

    // 수락
    public void confirm() {
        this.isConfirmed = true;
    }

}
