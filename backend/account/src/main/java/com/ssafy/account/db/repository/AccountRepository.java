package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.account.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByMemberIdAndAccountType(Long memberId, String accountType);
    List<Account> findByMemberId(Long memberId);
    Account findByPetNameAndAccountNumber(String petName, String accountNumber);

    List<Account> findAccountsByMemberId(Long memberId);

    Optional<Account> findByMemberIdAndAccountType(Long memberId, String accountType);

    Optional<Account> findByMemberIdAndAccountTypeAndAccountState(Long memberId, String accountType, String accountState);

    Optional<Account> findByRfidCodeAndAccountState(String rfidCode, String accountState);

    Optional<Account> findByAccountNumberAndAccountState(String accountNumber, String accountState);

    Optional<Account> findByIdAndAccountState(Long id, AccountState accountState);

    Optional<Account> findByDepositorNameAndAccountType(String depositorName, String accountType);

    Optional<Account> findByDepositorNameAndAccountNumber(String depositorName, String accountNumber);

    List<Account> findAccountByMemberIdAndAccountTypeAndAccountState(Long memberId, String accountType, String accountState);

    Optional<Account> findByAccountNumber(String accountNumber);

    long countByAccountType(String accountType);

    Long countByCreatedAtAfter(LocalDateTime startOfDay);

    Account findAccountById(Long id);
}

