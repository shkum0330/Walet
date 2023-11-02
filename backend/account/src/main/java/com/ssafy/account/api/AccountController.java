package com.ssafy.account.api;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.api.request.account.SelectChargingAccountRequest;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.api.status.SuccessCode;
import com.ssafy.account.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final HomeAccountService homeAccountService;
    private final PetHomeAccountService petHomeAccountService;
    private final TransactionService transactionService;
    private final S3Service s3Service;

    /*
     1. 동물병원
     2. 반려동물용품
     3. 반려동물미용
     4. 애견카페
     5. 반려견놀이터
     6. 반려동물장례
     */
    @PostMapping("/register/general-account")
    public ResponseEntity<?> registerGeneralAccount(@RequestBody AccountSaveRequest accountSaveRequest){
        log.info("일반/사업자 계좌 생성 요청 dto: {}", accountSaveRequest);
        Response response=new Response<Long>(200, SuccessCode.GENERAL_SUCCESS.getMessage(),
                accountService.registerGeneralAccount(accountSaveRequest));
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register/pet-account")
    public ResponseEntity<?> registerAnimalAccount(@RequestPart("petAccountRequest") PetAccountSaveRequest petAccountRequest,
                                                   @RequestPart("petImage") MultipartFile file) throws IOException {

        petAccountRequest.setPetPhoto(s3Service.getS3ImageUrl(s3Service.upload(file)));
        log.info("펫 계좌 생성 요청 dto: {}", petAccountRequest);
        Response response=new Response<Long>(200, SuccessCode.GENERAL_SUCCESS.getMessage(),
                accountService.registerPetAccount(petAccountRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/charging-account-list/{memberId}")
    public ResponseEntity<?> getChargingAccountList(@PathVariable Long memberId){ // memberId를 그대로 보낼것인가?
        Response response=new Response(200, SuccessCode.GENERAL_SUCCESS.getMessage(),
                accountService.getChargingAccountList(memberId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select-charging-account")
    public ResponseEntity<?> selectChargingAccount(@RequestBody SelectChargingAccountRequest request){
        Response response=new Response(200, SuccessCode.GENERAL_SUCCESS.getMessage(),
                accountService.selectChargingAccount(request));
        return ResponseEntity.ok(response);
    }
//    // todo: 일단 파라미터에 accountId라 했으나 memberId를 받아와서 거기서 계좌번호를 끌어내야함
//    @GetMapping("/home/account/{accountId}")
//
//    public ResponseEntity<?> getAccountInfo(@PathVariable Long accountId) {
//
//        Response response = new Response(200, SuccessCode.GENERAL_SUCCESS.getMessage(), homeAccountService.getHomeAccount(accountId));
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/home/transaction/{accountId}")
//    public ResponseEntity<?> getRecentTransactionList(@PathVariable Long accountId) {
//
//        Response response = new Response(200, "데이터 처리 성공", homeAccountService.getHomeTransactions(accountId));
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/tx-history/{accountId}")
//    public ResponseEntity<?> getTransactionsByDateRange(@PathVariable Long accountId,
//                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//        TransactionPeriodRequest request = new TransactionPeriodRequest(accountId, startDate, endDate);
//        Response response = new Response(200, "데이터 처리 성공", accountService.getSpecificPeriodTransaction(request));
//        return ResponseEntity.ok(response);
//    }
}
