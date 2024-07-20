package com.ssafy.account.api;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.RfidRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.transaction.PetInfoResponse;
import com.ssafy.account.api.response.transaction.ReceiverInfoResponse;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.ssafy.account.common.api.status.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // RFID로 펫 정보 조회
    @PostMapping("/transaction/pet-info")
    public Response<PetInfoResponse> getPetInfoByRfid(@RequestBody RfidRequest request) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getPetInfoByRfid(request.getRfidCode()));
    }

    @GetMapping("/transaction/recipient-info")
    public Response<ReceiverInfoResponse> getReceiverInfoByAccountNumber(@RequestParam String accountNumber, @RequestParam Long paymentAmount) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getReceiverInfoByAccountNumber(accountNumber, paymentAmount));
    }

    @PostMapping("/transaction/pet/create")
    public Response<?> addPetRelatedTransaction(@RequestBody TransactionRequest request) {
        Long transactionId = transactionService.addPetRelatedTransaction(request);
        return Response.ok(GENERAL_SUCCESS, transactionId);
    }

    @PostMapping("/transaction/remittance/create")
    public Response<?> addRemittanceTransaction(@RequestBody RemittanceRequest request) {
        Long transactionId = transactionService.addRemittanceTransaction(request);
        return Response.ok(GENERAL_SUCCESS, transactionId);
    }

    // 거래내역 목록 상단에 계좌정보 띄워주기
    @GetMapping("/transaction/account-info/{accountId}")
    public Response<?> getTransactionAccountDetail(@PathVariable Long accountId) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getTransactionAccountDetail(accountId));
    }

    @GetMapping("/transaction/list/{accountId}")
    public Response<?> getTransactionHistory(@RequestHeader("id") Long memberId, @PathVariable Long accountId) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getTransactionHistory(memberId, accountId));
    }

    @GetMapping("/transaction/in-specific-period/{accountId}")
    public Response<?> getTransactionsByDateRange(@RequestHeader("id") Long memberId, @PathVariable Long accountId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        TransactionPeriodRequest request = new TransactionPeriodRequest(accountId, startDate, endDate);
        return Response.ok(GENERAL_SUCCESS, transactionService.getSpecificPeriodTransaction(memberId, request));
    }

    @GetMapping("/transaction/detail/{transactionId}")
    public Response<?> getTransactionDetail(@PathVariable Long transactionId) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getTransactionDetail(transactionId));
    }
    
    // 관리자 페이지에 특정 계좌의 거래목록 반환
    @GetMapping("/admin/transaction/{accountId}")
    public Response<?> getTransactionsForAdmin(@PathVariable Long accountId) {
        return Response.ok(GENERAL_SUCCESS, transactionService.getTransactionsForAdmin(accountId));
    }
}
