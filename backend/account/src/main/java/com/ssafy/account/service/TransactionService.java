package com.ssafy.account.service;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.transaction.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionAccountResponse getTransactionAccountDetail(Long accountId); // 거래내역 페이지에서 계좌의 기본정보 반환

    PetInfoResponse getPetInfoByRfid(String rfidCode);// RFID코드를 바탕으로 동물 정보 반환
    ReceiverInfoResponse getReceiverInfoByAccountNumber(String accountNumber, Long paymentAmount);// 계좌번호를 바탕으로 금액 수령자의 정보 반환(이름, 계좌번호, 이체금액)
    Long addPetRelatedTransaction(TransactionRequest transactionRequest); // 반려동물 관련 상품 구매 거래내역 추가
    Long addRemittanceTransaction(RemittanceRequest remittanceRequest); // 일반계좌 간 송금 거래내역 추가
    Page<TransactionResponse> getTransactionHistory(Long memberId, Long accountId, int page); // 특정 계좌의 거래내역 목록 반환
    List<TransactionResponse> getSpecificPeriodTransaction(Long memberId, TransactionPeriodRequest request); // 특정 기간 내에 속하는 거래내역 목록 반환
    TransactionDetailResponse getTransactionDetail(Long transactionId); // 특정 거래 내역의 상세정보 반환

    List<AdminMemberAccountTransactionResponse> getTransactionsForAdmin(Long accountId);
}
