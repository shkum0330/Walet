package com.ssafy.account.service.impl;

import com.ssafy.account.api.response.account.AnimalAccountDetailResponse;
import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.PetHomeAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.NO_PET_ACCOUNT;

@Service
@Transactional
@AllArgsConstructor
public class PetHomeAccountServiceImpl implements PetHomeAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TimeUtil timeUtil;

    @Override
    public List<AnimalAccountDetailResponse> getAnimalAccountDetail(Long memberId) {
        // 해당 사용자의 펫계좌 목록을 가져옴
        List<Account> petAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "02");

        if(petAccounts.isEmpty()) {
            throw new NotFoundException(NO_PET_ACCOUNT);
        }

        List<HomeTransactionResponse> top5Transactions = new ArrayList<>();
        List<AnimalAccountDetailResponse> result = new ArrayList<>();

        for (Account petAccount : petAccounts) {

            // 각 계좌의 거래내역을 가져옴
            List<Transaction> transactions = transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(petAccount);
            for (Transaction transaction : transactions) {
                TransactionType transactionType = transaction.getTransactionType();

                if(transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getAccount().getDepositorName()));
                }
                else if (transactionType == TransactionType.WITHDRAWAL) {
                    top5Transactions.add(new HomeTransactionResponse(transaction, transaction.getRecipient()));
                }
            }

            result.add(new AnimalAccountDetailResponse(petAccount, top5Transactions, timeUtil.calculateAge(petAccount.getPetBirth())));
        }

        return result;
    }

//    @Override
//    public List<HomeTransactionResponse> getHomeTransactions(Long accountId) {
//        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
//        List<Transaction> transactions=transactionRepository.findTop5ByAccountOrderByTransactionTimeDesc(account);
//        return transactions.stream().map(HomeTransactionResponse::new).collect(Collectors.toList());
//    }
}
