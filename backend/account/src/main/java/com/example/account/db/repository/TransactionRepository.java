package com.example.account.db.repository;

import com.example.account.db.entity.account.Account;
import com.example.account.db.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
    List<Transaction> findTop5ByAccountOrderByTransactionTimeDesc(Account account);

    List<Transaction> findByTransactionTimeBetweenOrderByTransactionTimeDesc(LocalDateTime startDate, LocalDateTime endDate);

}
