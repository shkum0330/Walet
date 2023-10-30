package com.example.account.api;

import com.example.account.api.request.TransactionPeriodRequest;
import com.example.account.common.api.Response;
import com.example.account.common.api.status.SuccessCode;
import com.example.account.service.AccountService;
import com.example.account.service.HomeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transaction;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController{

    private final HomeAccountService homeAccountService;
    private final AccountService accountService;

    // todo: 일단 파라미터에 accountId라 했으나 memberId를 받아와서 거기서 계좌번호를 끌어내야함
    @GetMapping("/home/account/{accountId}")
    public ResponseEntity<?> getAccountInfo(@PathVariable Long accountId){

        Response response = new Response(200,SuccessCode.GENERAL_SUCCESS.getMessage(),homeAccountService.getHomeAccount(accountId));

        return ResponseEntity.ok(response);
    }
    @GetMapping("/home/transaction/{accountId}")
    public ResponseEntity<?> getRecentTransactionList(@PathVariable Long accountId){

        Response response =new Response(200,"데이터 처리 성공",homeAccountService.getHomeTransactions(accountId));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tx-history/{accountId}")
    public ResponseEntity<?> getTransactionsByDateRange(@PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        TransactionPeriodRequest request=new TransactionPeriodRequest(accountId,startDate,endDate);
        Response response =new Response(200,"데이터 처리 성공",accountService.getSpecificPeriodTransaction(request));
        return ResponseEntity.ok(response);
    }
}
