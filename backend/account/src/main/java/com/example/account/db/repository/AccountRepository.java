package com.example.account.db.repository;

import com.example.account.db.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByMemberIdAndAccountType(Long memberId, String accountType);

    List<Account> findAccountsByMemberId(Long memberId);
}
