package com.ssafy.account.service.impl;

import com.ssafy.account.api.response.account.AnimalAccountDetailResponse;
import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.PetHomeAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.NO_ACCOUNT;

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
