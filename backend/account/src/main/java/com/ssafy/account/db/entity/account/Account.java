package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.common.domain.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

import static com.ssafy.account.db.entity.account.Account.AccountState.*;
import static com.ssafy.account.db.entity.account.Account.AccountType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    @Column(name = "member_id")
    private Long memberId;

    // 농협 API 전용
    @Column(name = "pin_account",length = 40)
    private String pinAccount;
    @Column(name = "virtual_account", length = 20)
    private String virtualAccount;

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

    @Enumerated(EnumType.STRING)
    @Column(name="account_state",length=10, nullable = false)
    private AccountState accountState; // 상태 => 정상(00), 잠금(01), 정지(10), 폐쇄(11)

    public enum AccountState {
        ACTIVE("00"),
        LOCKED("01"),
        STOPPED("10"),
        CLOSED("11");

        private final String code;

        AccountState(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static AccountState getState(String code) {
            for (AccountState state : values()) {
                if (state.getCode().equals(code)) {
                    return state;
                }
            }
            throw new IllegalArgumentException("Unknown account state code: " + code);
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name="account_type", length = 10,nullable = false)
    private AccountType accountType; // 타입(일반계좌(00), 사업자계좌(01), 펫계좌(02))

    @Getter
    public enum AccountType {
        NORMAL("00"),
        BUSINESS("01"),
        PET("02");

        private final String code;

        AccountType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static AccountType getType(String code) {
            for (AccountType type : values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("잘못된 타입 코드: " + code);
        }
    }
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
    private Integer limitTypes; // 사용가능 제한업종 목록(비트연산으로 추가)

    // 일반계좌 기본정보 입력
    public Account(Long memberId, String memberName, AccountSaveRequest accountSaveRequest) {
        this.accountState = ACTIVE;
        this.memberId = memberId;
        this.accountName= accountSaveRequest.getAccountName();
        this.depositorName = memberName;
        this.accountType = accountSaveRequest.getAccountType();
        this.linkedAccountId = accountSaveRequest.getLinkedAccountId();
    }

    // 반려동물계좌 기본정보 입력
    public Account(Long memberId, String memberName, PetAccountSaveRequest accountRequest) {
        this.accountState = ACTIVE;
        this.memberId = memberId;
        this.accountName=accountRequest.getAccountName();
        this.depositorName = memberName;
        this.accountType = PET;
        this.linkedAccountId = accountRequest.getLinkedAccountId();
        this.petName = accountRequest.getPetName();
        this.petGender = accountRequest.getPetGender(); // 펫성별
        this.petBirth = accountRequest.getPetBirth(); // 펫생년월일
        this.petBreed = accountRequest.getPetBreed(); // 품종
        this.petNeutered = accountRequest.getPetNeutered(); // 중성화여부
        this.petWeight = accountRequest.getPetWeight(); // 몸무게
        this.petPhoto = accountRequest.getPetPhoto(); // 사진
    }

    public void addHashPwd(String hashAccountPwd) {
        this.accountPassword = hashAccountPwd;
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
        this.accountState = ACTIVE;
    }
    // 2. LOCKED
    public void updateStateToLocked() {
        this.accountState = LOCKED;
    }
    // 3. SUSPENDED
    public void updateStateToStopped() {
        this.accountState = STOPPED;
    }
    // 4. CLOSED
    public void updateStateToClosed() {
        this.accountState = CLOSED;
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
        this.accountType=NORMAL;
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
