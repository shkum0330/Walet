package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PetInfoResponse { // rfid 코드를 바탕으로 알아낸 펫정보
    private Long accountId; // 펫계좌 PK
    private String petName; // 펫이름
    private String petGender; // 펫성별
    private LocalDate petBirth; // 펫생년월일
    private String petBreed; // 품종
    private Boolean petNeutered; // 중성화여부
    private LocalDate petRegistrationDate; // 등록일
    private Float petWeight; // 몸무게
    private String petPhoto; // 사진

    public PetInfoResponse(Account account) {
        this.accountId = account.getId();
        this.petName = account.getPetName();
        this.petGender = account.getPetGender();
        this.petBirth = account.getPetBirth();
        this.petBreed = account.getPetBreed();
        this.petNeutered = account.getPetNeutered();
        this.petRegistrationDate = account.getPetRegistrationDate();
        this.petWeight = account.getPetWeight();
        this.petPhoto = account.getPetPhoto();
    }
}
