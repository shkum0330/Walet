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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.NO_ACCOUNT;
import static com.ssafy.account.common.api.status.FailCode.NO_NORMAL_ACCOUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeAccountServiceImpl implements HomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 일반계좌 목록을 가져옴
    @Override
    public List<HomeAccountResponse> getHomeAccountDetail(Long memberId) {
        List<Account> normalAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "00");

        if(normalAccounts.isEmpty()) {
            throw new NotFoundException(NO_NORMAL_ACCOUNT);
        }

        List<HomeAccountResponse> result = new ArrayList<>();
        for (Account normalAccount : normalAccounts) {
            List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(normalAccount);
            result.add(new HomeAccountResponse(normalAccount, transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList())));
        }

        return result;
    }
}
