package com.example.account.service;

import com.example.account.api.request.account.AccountSaveRequest;
import com.example.account.api.request.account.AnimalAccountSaveRequest;
import com.example.account.api.request.account.AssignRequest;
import com.example.account.api.request.account.SelectChargingAccountRequest;
import com.example.account.api.response.account.ChargingAccountResponse;
import com.example.account.api.response.transaction.MonthlyExpenditureDetailResponse;

import java.util.List;
import java.util.Map;

public interface AccountService {

    Long registerGeneralAccount(AccountSaveRequest accountSaveRequest);
    Long registerAnimalAccount(AnimalAccountSaveRequest animalAccountRequest);

    List<ChargingAccountResponse> getChargingAccountList(Long memberId);
    Long selectChargingAccount(SelectChargingAccountRequest request);
    Long assignAccount(AssignRequest assignRequest);
    void updateStateToActive(Long accountId);
    void updateStateToLocked(Long accountId);
    void updateStateToSuspended(Long accountId);
    void updateStateToClosed(Long accountId);
    MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId);
    Map<Integer, Long> getCategoryExpenditureDetail(Long accountId);

}
