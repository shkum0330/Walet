package com.ssafy.account.service;

import com.ssafy.account.api.response.account.HomeAccountResponse;
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

import static com.ssafy.account.common.api.status.FailCode.NO_NORMAL_ACCOUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 일반계좌 목록을 가져옴
    public List<HomeAccountResponse> getHomeAccountDetail(Long memberId) {
        List<Account> normalAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "00");

        if(normalAccounts.isEmpty()) {
            throw new NotFoundException(NO_NORMAL_ACCOUNT);
        }

        List<HomeTransactionResponse> top5Transactions = new ArrayList<>();
        List<HomeAccountResponse> result = new ArrayList<>();

        for (Account normalAccount : normalAccounts) {

            List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(normalAccount);
            for (Transaction transaction : transactions) {

                TransactionType transactionType = transaction.getTransactionType();

                if(transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getAccount().getDepositorName()));
                }
                else if (transactionType == TransactionType.WITHDRAWAL) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getRecipient()));
                }

            }

            result.add(new HomeAccountResponse(normalAccount, top5Transactions));
        }

        return result;
    }
}
