package com.example.account.service;

import com.example.account.api.request.*;
import com.example.account.api.response.*;

import java.util.List;
import java.util.Map;

public interface AccountService {

//    GeneralAccountDetailResponse getGeneralAccountDetail(Long accountId);
    AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId);
    Long addTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse> getTransactionHistory(Long accountId);
    List<TransactionResponse> getSpecificPeriodTransaction(TransactionPeriodRequest request);
    Long registerGeneralAccount(AccountSaveRequest accountSaveRequest);
    Long registerAnimalAccount(AnimalAccountSaveRequest animalAccountRequest);
    Long assignAccount(AssignRequest assignRequest);
    void updateStateToActive(Long accountId);
    void updateStateToLocked(Long accountId);
    void updateStateToSuspended(Long accountId);
    void updateStateToClosed(Long accountId);
    MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId);
    Map<Integer, Long> getCategoryExpenditureDetail(Long accountId);

}
