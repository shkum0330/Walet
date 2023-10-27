package com.example.account.api;

import com.example.account.common.api.Response;
import com.example.account.common.api.Response2;
import com.example.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController{

    private final AccountService accountService;

    // todo: 일단 파라미터에 accountId라 했으나 memberId를 받아와서 거기서 계좌번호를 끌어내야함
    @GetMapping("/home/{accountId}")
    public ResponseEntity<?> getRecentTransactionList(@PathVariable Long accountId){


        Response response =new Response(200,"데이터 처리 성공",accountService.getHomeTransactions(accountId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
