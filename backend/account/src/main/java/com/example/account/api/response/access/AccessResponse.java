package com.example.account.api.response.access;

import com.example.account.db.entity.access.Access;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessResponse {
    private Long id;
    private String requesterName;
    private String animalName; // 상대방 반려동물 이름
    private String content; // 상대방에게 보낼 메시지
    private String accountNumber; // 접근신청할 계좌번호
    private LocalDateTime requestedTime; // 요청시간
    private LocalDateTime acceptedTime; // 수락시간

    public AccessResponse (Access access) {
        this.id = access.getId();
        this.requesterName = access.getRequestMemberName();
        this.animalName = access.getAnimalName();
        this.content = access.getContent();
        this.accountNumber = access.getAccountNumber();
        this.requestedTime = access.getCreatedAt();
        this.acceptedTime = access.getUpdatedAt();
    }
}
