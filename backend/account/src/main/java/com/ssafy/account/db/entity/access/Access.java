package com.ssafy.account.db.entity.access;

import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.common.domain.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Access extends BaseTimeEntity { // 계좌 접근 신청 및 권한 여부 확인

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long id;
    @Column(name = "pet_name", nullable = false)
    private String petName; // 상대방 반려동물 이름
    @Column(name = "content", nullable = false)
    private String content; // 상대방에게 보낼 메시지
    @Column(name = "request_member_id", nullable = false)
    private Long requestMemberId; // 접근요청한 사람의 memberId
    @Column(name = "request_member_name", nullable = false)
    private String requestMemberName; // 접근요청한 사람의 이름
    @Column(name = "account_number", nullable = false)
    private String accountNumber; // 접근신청할 계좌번호
    @Column(name = "is_confirmed")
    private int isConfirmed; // 수락여부 -> 수락이 됐으면 권한이 생성된 것(0: 아직 수락되지 않은 상태 / 1: 수락된 상태)

    public Access(Long memberId, String memberName, AccessSaveRequest request) {
        this.petName = request.getPetName();
        this.content = request.getContent();
        this.requestMemberId = memberId;
        this.requestMemberName = memberName;
        this.accountNumber = request.getAccountNumber();
        this.isConfirmed = 0;
    }

    // 수락
    // 수락이 됐으면 해당 계좌의 거래내역에 접근 가능
    // 수락이 되지 않았으면 요청을 받은 상대에게 요청리스트가 쌓임
    public void confirm() {
        this.isConfirmed = 1;
    }

}
