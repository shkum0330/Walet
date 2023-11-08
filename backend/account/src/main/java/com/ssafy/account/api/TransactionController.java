package com.ssafy.account.api;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.ssafy.account.common.api.status.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transaction/pet-info")
    public Response getPetInfoByRfid(@RequestParam String rfidCode) {
        return Response.success(GENERAL_SUCCESS, transactionService.getPetInfoByRfid(rfidCode));
    }

    @GetMapping("/transaction/recipient-info")
    public Response getReceiverInfoByAccountNumber(@RequestParam String accountNumber, @RequestParam Long paymentAmount) {
        return Response.success(GENERAL_SUCCESS, transactionService.getReceiverInfoByAccountNumber(accountNumber, paymentAmount));
    }

    @PostMapping("/transaction/pet/create")
    public Response addPetRelatedTransaction(@RequestBody TransactionRequest request) {
        Long transactionId = transactionService.addPetRelatedTransaction(request);
        return Response.success(GENERAL_SUCCESS, transactionId);
    }

    @PostMapping("/transaction/remittance/create")
    public Response addRemittanceTransaction(@RequestBody RemittanceRequest request) {
        Long trasactionId = transactionService.addRemittanceTransaction(request);
        return Response.success(GENERAL_SUCCESS, trasactionId);
    }

    // 거래내역 목록 상단에 계좌정보 띄워주기
    @GetMapping("/transaction/account-info/{accountId}")
    public Response getTransactionAccountDetail(@PathVariable Long accountId) {
        return Response.success(GENERAL_SUCCESS, transactionService.getTransactionAccountDetail(accountId));
    }

    @GetMapping("/transaction/list/{accountId}")
    public Response getTransactionHistory(@RequestHeader("id") Long memberId, @PathVariable Long accountId) {
        return Response.success(GENERAL_SUCCESS, transactionService.getTransactionHistory(memberId, accountId));
    }

    @GetMapping("/transaction/in-specific-period/{accountId}")
    public Response getTransactionsByDateRange(@RequestHeader("id") Long memberId, @PathVariable Long accountId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        TransactionPeriodRequest request = new TransactionPeriodRequest(accountId, startDate, endDate);
        return Response.success(GENERAL_SUCCESS, transactionService.getSpecificPeriodTransaction(memberId, request));
    }

    @GetMapping("/transaction/detail/{transactionId}")
    public Response getTransactionDetail(@PathVariable Long transactionId) {
        return Response.success(GENERAL_SUCCESS, transactionService.getTransactionDetail(transactionId));
    }
}
