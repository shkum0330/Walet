package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.common.domain.util.BaseTimeEntity;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.ssafy.account.db.entity.account.AccountState.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account extends BaseTimeEntity {
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

    @Column(name="linked_account_id", length = 20)
    private Long linkedAccountId; // 연결될 충전계좌 아이디(선택사항)

    public Account(Long memberId,String depositorName,AccountSaveRequest accountSaveRequest){
        this.accountState = ACTIVE; // 활성 상태라는 의미이다.
        this.memberId = memberId;
        this.depositorName = depositorName;
        this.accountPassword= PasswordEncoder.hashPassword(accountSaveRequest.getAccountPassword());
        this.accountName= accountSaveRequest.getAccountName();
        this.linkedAccountId = accountSaveRequest.getLinkedAccountId();
        this.accountNumber=accountSaveRequest.getAccountNumber();
    }

    public Account(Long memberId, String depositorName, PetAccountSaveRequest accountSaveRequest){
        this.accountState = ACTIVE; // 활성 상태라는 의미이다.
        this.memberId = memberId;
        this.depositorName = depositorName;
        this.accountPassword= PasswordEncoder.hashPassword(accountSaveRequest.getAccountPassword());
        this.accountName= accountSaveRequest.getAccountName();
        this.linkedAccountId = accountSaveRequest.getLinkedAccountId();
        this.accountNumber=accountSaveRequest.getAccountNumber();
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
}
