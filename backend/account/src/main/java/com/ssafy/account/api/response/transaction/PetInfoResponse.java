package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.Account;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PetInfoResponse { // rfid 코드를 바탕으로 알아낸 펫정보
    private Long accountId; // 펫계좌 PK
    private String petName; // 펫이름
    private String petGender; // 펫성별
    private String petBirth; // 펫생년월일
    private String petAge; // 나이
    private String petBreed; // 품종
    private String petNeutered; // 중성화여부
    private String petPhoto; // 사진

    @Builder
    public PetInfoResponse(Long accountId, String petName, String petGender, String petBirth, String petAge,
                           String petBreed, String petNeutered, String petPhoto) {
        this.accountId = accountId;
        this.petName = petName;
        this.petGender = petGender;
        this.petBirth = petBirth;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petNeutered = petNeutered;
        this.petPhoto = petPhoto;
    }
}
