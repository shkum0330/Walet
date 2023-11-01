package com.example.account.service;

import com.example.account.api.request.account.AccountSaveRequest;
import com.example.account.api.request.account.PetAccountSaveRequest;
import com.example.account.api.request.account.AssignRequest;
import com.example.account.api.request.account.SelectChargingAccountRequest;
import com.example.account.api.response.account.AccountResponse;
import com.example.account.api.response.account.ChargingAccountResponse;
import com.example.account.api.response.transaction.MonthlyExpenditureDetailResponse;

import java.util.List;
import java.util.Map;

public interface AccountService {

    Long registerGeneralAccount(AccountSaveRequest accountSaveRequest); // 일반계좌 생성
    Long registerPetAccount(PetAccountSaveRequest animalAccountRequest); // 동물계좌 생성

    List<ChargingAccountResponse> getChargingAccountList(Long memberId); // 충전계좌로 연결할 수 있는 일반계좌의 목록 반환

    List<AccountResponse> getAllAccountList(Long memberId); // 관리자 페이지에 선택된 유저의 모든 계좌 목록을 보내줌
    Long selectChargingAccount(SelectChargingAccountRequest request); // 사용자가 가지고 있는 일반 계좌 중 하나를 반려동물 계좌의 충전계좌로 선택
    Long assignAccount(AssignRequest assignRequest); // 반려동물계좌 양도
    void updateStateToActive(Long accountId); // 계좌 상태를 정상으로 변경
    void updateStateToLocked(Long accountId); // 계좌 상태를 잠금으로 변경
    void updateStateToSuspended(Long accountId); // 계좌 상태를 정지로 변경
    void updateStateToClosed(Long accountId); // 계좌 상태를 폐쇄로 변경
    MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId); // 이번 달의 지출 현황을 카테고리별로 분류
    Map<Integer, Long> getCategoryExpenditureDetail(Long accountId); // 전체 지출을 카테고리 별로 분류해서 반환

}
