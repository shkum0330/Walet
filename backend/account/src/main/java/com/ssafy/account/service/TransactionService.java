package com.ssafy.account.service;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.transaction.TransactionAccountResponse;
import com.ssafy.account.api.response.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionAccountResponse getHomeAccountDetail(Long accountId); // 거래내역 페이지에서 계좌의 기본정보 반환

    Long addPetRelatedTransaction(TransactionRequest transactionRequest); // 반려동물 관련 상품 구매 거래내역 추가

    Long addRemittanceTransaction(RemittanceRequest remittanceRequest); // 일반계좌 간 송금 거래내역 추가
    List<TransactionResponse> getTransactionHistory(Long memberId, Long accountId); // 특정 계좌의 거래내역 목록 반환
    List<TransactionResponse> getSpecificPeriodTransaction(Long memberId, TransactionPeriodRequest request); // 특정 기간 내에 속하는 거래내역 목록 반환
}
