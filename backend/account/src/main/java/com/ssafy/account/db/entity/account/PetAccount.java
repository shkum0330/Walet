package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@ToString
@DiscriminatorValue("Pet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetAccount extends Account {

    @Column(name="business_type")
    private Integer businessType; // 사업자계좌면 사업유형도 입력
    // 펫 정보(일반 계좌에서는 이 값들이 null값으로 들어감)
    @Column(name="pet_name", length = 10)
    private String petName; // 펫이름
    @Column(name = "pet_type",length = 5)
    private String petType; // 강아지, 고양이
    @Column(name="pet_gender", length = 3)
    private String petGender; // 펫성별
    @Column(name="pet_birth")
    private LocalDate petBirth; // 펫생년월일
    @Column(name="pet_breed", length = 30)
    private String petBreed; // 품종
    @Column(name="pet_neutered")
    private Boolean petNeutered; // 중성화여부
    @Column(name="pet_weight")
    private Float petWeight; // 몸무게
    @Column(name="pet_photo",length = 100)
    private String petPhoto; // 사진
    @Column(name="rfid_code", length = 64)
    private String rfidCode; // 강아지 RFID 코드
    @Column(name="limit_types")
    private Integer limitTypes=0; // 사용가능 제한업종 목록(비트연산으로 추가)

    // 반려동물계좌 기본정보 입력
    @Builder
    public PetAccount(Long memberId, String memberName, PetAccountSaveRequest petAccountSaveRequest) {
        super(memberId, memberName, petAccountSaveRequest);
        this.petName = petAccountSaveRequest.getPetName();
        this.petGender = petAccountSaveRequest.getPetGender(); // 펫성별
        this.petBirth = petAccountSaveRequest.getPetBirth(); // 펫생년월일
        this.petBreed = petAccountSaveRequest.getPetBreed(); // 품종
        this.petNeutered = petAccountSaveRequest.getPetNeutered(); // 중성화여부
        this.petWeight = petAccountSaveRequest.getPetWeight(); // 몸무게
        this.petPhoto = petAccountSaveRequest.getPetPhoto(); // 사진
        this.rfidCode= PasswordEncoder.hashPassword(petAccountSaveRequest.getRfidCode()); // 반려동물의 등록정보
    }

    // 제한업종 추가
    public void addLimitType(int typeNum) {
        this.limitTypes |= typeNum;
    }

    // 양도시 정보 이전
    public void transferPetInfo(PetAccount account){
        this.limitTypes= account.getLimitTypes();
        this.petBirth= account.getPetBirth();
        this.petBreed= account.getPetBreed();
        this.petGender= account.getPetGender();
        this.petName= account.getPetName();
        this.petNeutered= account.getPetNeutered();
        this.petPhoto= account.getPetPhoto();
        this.petType= account.getPetType();
        this.petWeight= account.getPetWeight();
        this.rfidCode= account.getRfidCode();
    }

    public void deletePetInfo(){
        this.limitTypes=null;
        this.petBirth=null;
        this.petBreed=null;
        this.petGender=null;
        this.petName=null;
        this.petNeutered=null;
        this.petPhoto=null;
        this.petType=null;
        this.petWeight=null;
        this.rfidCode=null;
    }
}
