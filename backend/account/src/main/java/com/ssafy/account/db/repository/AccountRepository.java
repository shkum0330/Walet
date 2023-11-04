package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByMemberIdAndAccountType(Long memberId, String accountType);

    Account findAccountByPetNameAndAccountNumber(String petName, String accountNumber);

    List<Account> findAccountsByMemberId(Long memberId);

    Optional<Account> findByMemberIdAndAccountType(Long memberId, String accountType);
}
