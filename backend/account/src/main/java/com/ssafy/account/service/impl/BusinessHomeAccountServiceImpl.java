package com.ssafy.account.service.impl;

import com.ssafy.account.api.response.account.BusinessAccountDetailResponse;
import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.BusinessHomeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessHomeAccountServiceImpl implements BusinessHomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<BusinessAccountDetailResponse> getBusinessAccountDetail(Long memberId) {

        // 사업자 계좌 목록을 가져옴
        List<Account> businessAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "01");
        
        // 사업자 계좌가 없다면
        // 예외발생
        if(businessAccounts.isEmpty()) {
            throw new NotFoundException(NO_BUSINESS_ACCOUNT);
        }

        List<BusinessAccountDetailResponse> result = new ArrayList<>();
        for (Account businessAccount : businessAccounts) {
            List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(businessAccount);
            result.add(new BusinessAccountDetailResponse(businessAccount, transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList())));
        }
        return result;
    }
}
