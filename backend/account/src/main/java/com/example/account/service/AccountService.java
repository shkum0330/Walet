package com.example.account.service;

import com.example.account.api.request.account.AccountSaveRequest;
import com.example.account.api.request.account.AnimalAccountSaveRequest;
import com.example.account.api.request.account.AssignRequest;
import com.example.account.api.request.account.SelectChargingAccountRequest;
import com.example.account.api.response.account.AccountResponse;
import com.example.account.api.response.account.ChargingAccountResponse;
import com.example.account.api.response.transaction.MonthlyExpenditureDetailResponse;

import java.util.List;
import java.util.Map;

public interface AccountService {

    Long registerGeneralAccount(AccountSaveRequest accountSaveRequest); // 일반계좌 생성
    Long registerAnimalAccount(AnimalAccountSaveRequest animalAccountRequest); // 동물계좌 생성

    List<ChargingAccountResponse> getChargingAccountList(Long memberId); // 충전계좌로 연결할 수 있는 일반계좌의 목록 반환

    List<AccountResponse> getAllAccountList(Long memberId); // 관리자 페이지에 선택된 유저의 모든 계좌 목록을 보내줌
    Long selectChargingAccount(SelectChargingAccountRequest request);
    Long assignAccount(AssignRequest assignRequest);
    void updateStateToActive(Long accountId);
    void updateStateToLocked(Long accountId);
    void updateStateToSuspended(Long accountId);
    void updateStateToClosed(Long accountId);
    MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId);
    Map<Integer, Long> getCategoryExpenditureDetail(Long accountId);

}
