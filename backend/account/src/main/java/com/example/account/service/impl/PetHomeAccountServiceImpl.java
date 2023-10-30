package com.example.account.service.impl;

import com.example.account.api.response.AnimalAccountDetailResponse;
import com.example.account.api.response.HomeTransactionResponse;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.db.entity.Account;
import com.example.account.db.entity.Transaction;
import com.example.account.db.repository.AccountRepository;
import com.example.account.db.repository.TransactionRepository;
import com.example.account.service.PetHomeAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.account.common.api.status.FailCode.NO_ACCOUNT;

@Service
@Transactional
@AllArgsConstructor
public class PetHomeAccountServiceImpl implements PetHomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(account);
        AnimalAccountDetailResponse result = new AnimalAccountDetailResponse(account, transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList()));
        return result;
    }

//    @Override
//    public List<HomeTransactionResponse> getHomeTransactions(Long accountId) {
//        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
//        List<Transaction> transactions=transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(account);
//        return transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList());
//    }
}
