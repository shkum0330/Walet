package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.common.domain.util.BaseTimeEntity;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;

import static com.ssafy.account.db.entity.account.AccountState.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id; // 계좌의 pk
    @Column(name = "member_id")
    private Long memberId;

//    @Column(name = "pin_account",length = 40)
//    private String pinAccount;
//    @Column(name = "virtual_account", length = 20)
//    private String virtualAccount;

    @Column(name="account_name",length = 20,nullable = false)
    private String accountName; // 계좌명(ex. NH올원e예금)
    @Column(name="account_number",length = 20,nullable = false)
    private String accountNumber; // 계좌번호
    @Column(name="depositor_name",length = 20,nullable = false)
    private String depositorName;// 예금주명
    @Column(name = "account_password", length = 64, nullable = false)
    private String accountPassword; // 계좌 비밀번호
    @Column(name="balance", nullable = false)
    private Long balance = 0L; // 잔액

    @Column(name="account_state",length=10, nullable = false)
    private String accountState; // 상태 => 정상(00), 잠금(01), 정지(10), 폐쇄(11)

    @Column(name="account_type", length = 10,nullable = false)
    private String accountType; // 타입(일반계좌(00), 사업자계좌(01), 펫계좌(02))
    @Column(name="business_type")
    private Integer businessType; // 사업자계좌면 사업유형도 입력
    @Column(name="linked_account_id", length = 20)
    private Long linkedAccountId; // 연결될 충전계좌 아이디(선택사항)

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

    // 일반계좌 기본정보 입력
    @Builder(builderMethodName = "generalAccountBuilder", buildMethodName = "buildGeneralAccount")
    public Account(Long memberId, String depositorName, AccountSaveRequest accountSaveRequest) {
        this.accountState = "00";
        this.memberId = memberId;
        this.depositorName = depositorName;
        this.accountPassword= PasswordEncoder.hashPassword(accountSaveRequest.getAccountPassword());
        this.accountName= accountSaveRequest.getAccountName();
        this.accountType = accountSaveRequest.getAccountType();
        this.businessType=accountSaveRequest.getBusinessType();
        this.linkedAccountId = accountSaveRequest.getLinkedAccountId();
    }

    // 반려동물계좌 기본정보 입력
    @Builder(builderMethodName = "petAccountBuilder", buildMethodName = "buildPetAccount")
    public Account(Long memberId, String memberName, PetAccountSaveRequest petAccountSaveRequest) {
        this.accountState = "00";
        this.memberId = memberId;
        this.accountName=petAccountSaveRequest.getAccountName();
        this.depositorName = memberName;
        this.accountPassword= PasswordEncoder.hashPassword(petAccountSaveRequest.getAccountPassword());
        this.accountType = "02";
        this.linkedAccountId = petAccountSaveRequest.getLinkedAccountId();
        this.petName = petAccountSaveRequest.getPetName();
        this.petGender = petAccountSaveRequest.getPetGender(); // 펫성별
        this.petBirth = petAccountSaveRequest.getPetBirth(); // 펫생년월일
        this.petBreed = petAccountSaveRequest.getPetBreed(); // 품종
        this.petNeutered = petAccountSaveRequest.getPetNeutered(); // 중성화여부
        this.petWeight = petAccountSaveRequest.getPetWeight(); // 몸무게
        this.petPhoto = petAccountSaveRequest.getPetPhoto(); // 사진
        this.rfidCode= PasswordEncoder.hashPassword(petAccountSaveRequest.getRfidCode());
    }

    public void createAccountNumber(){
        int length = 13;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        accountNumber = sb.toString();
    }
    public void addHashedRfid(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    // 제한업종 추가
    public void addLimitType(int typeNum) {
        this.limitTypes |= typeNum;
    }

    // 사업자계좌에는 사업유형도 입력
    public void addBusinessType(int type) {
        this.businessType = type;
    }

    // 계좌번호 부여
    public void createAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    // 충전계좌 추가
    public void addLinkedAccount(Long linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    // 입금
    public void addBalance(Long money) {
        this.balance += money;
    }

    // 출금 및 이체
    public void minusBalance(Long money) {
        this.balance -= money;
    }

    // 계좌 상태 변경
    // 1. ACTIVE
    public void updateStateToActive() {
        this.accountState = "00";
    }
    // 2. LOCKED
    public void updateStateToLocked() {
        this.accountState = "01";
    }
    // 3. SUSPENDED
    public void updateStateToSuspended() {
        this.accountState = "10";
    }
    // 4. CLOSED
    public void updateStateToClosed() {
        this.accountState = "11";
    }

    // 양도시 정보 이전
    public void transferPetInfo(Account account){
        this.accountType=account.getAccountType();
        this.limitTypes=account.getLimitTypes();
        this.petBirth=account.getPetBirth();
        this.petBreed=account.getPetBreed();
        this.petGender=account.getPetGender();
        this.petName=account.getPetName();
        this.petNeutered=account.getPetNeutered();
        this.petPhoto=account.getPetPhoto();
        this.petType=account.getPetType();
        this.petWeight=account.getPetWeight();
        this.rfidCode=account.getRfidCode();
    }
    public void deletePetInfo(){
        this.accountType="00";
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
