package com.ssafy.account.service.impl;

import com.ssafy.account.api.response.account.HomeAccountResponse;
import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.HomeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.NO_ACCOUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeAccountServiceImpl implements HomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public HomeAccountResponse getHomeAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> transactions=transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(account);
        return new HomeAccountResponse(account, transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList()));
    }
}
