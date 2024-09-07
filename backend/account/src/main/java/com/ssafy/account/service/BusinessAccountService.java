package com.ssafy.account.service;

import com.ssafy.account.api.response.account.BusinessAccountDetailResponse;
import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.account.common.api.status.FailCode.NO_BUSINESS_ACCOUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessAccountService {

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

        List<HomeTransactionResponse> top5Transactions = new ArrayList<>();
        List<BusinessAccountDetailResponse> result = new ArrayList<>();

        for (Account businessAccount : businessAccounts) {

            List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(businessAccount);
            for (Transaction transaction : transactions) {

                TransactionType transactionType = transaction.getTransactionType();

                if(transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getAccount().getDepositorName()));
                }
                else if (transactionType == TransactionType.WITHDRAWAL) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getRecipient()));
                }

            }

            result.add(new BusinessAccountDetailResponse(businessAccount, top5Transactions));
        }

        return result;
    }
}
