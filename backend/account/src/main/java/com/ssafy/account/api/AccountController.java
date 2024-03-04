package com.ssafy.account.api;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.AdminAllMemberIdsRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.api.request.account.SelectChargingAccountRequest;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ssafy.account.common.api.status.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final HomeAccountService homeAccountService;
    private final BusinessHomeAccountService businessHomeAccountService;
    private final PetHomeAccountService petHomeAccountService;
    private final S3Service s3Service;

    /*
     동물 및 관련 용품 소매업: 523996
     동물 장묘 및 보호 서비스업 : 930919
     1. 동물병원 1
     2. 반려동물용품 2
     3. 반려동물미용 4
     4. 애견카페 8
     5. 반려견놀이터 16
     */
    
    // 1. 계좌 생성
    @PostMapping("/register/general-account")
    public ResponseEntity<?> registerGeneralAccount(@RequestHeader("id") Long memberId, @RequestBody AccountSaveRequest accountSaveRequest){
        Response<Account> response=new Response<Account>(200, GENERAL_SUCCESS.getMessage(),
                accountService.registerGeneralAccount(memberId, accountSaveRequest));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/pet-account")
    public ResponseEntity<?> registerAnimalAccount(@RequestHeader("id") Long memberId, @RequestPart("petAccountRequest") PetAccountSaveRequest petAccountRequest,
                                                   @RequestPart("petImage") MultipartFile file) throws IOException {

        petAccountRequest.setPetPhoto(s3Service.getS3ImageUrl(s3Service.upload(file)));
        log.info("펫 계좌 생성 요청 dto: {}", petAccountRequest);
        Response response=new Response<Long>(200, GENERAL_SUCCESS.getMessage(),
                accountService.registerPetAccount(memberId, petAccountRequest));
        return ResponseEntity.ok(response);
    }
    
    // 2 메인페이지 계좌 정보
    // 2-1. 일반계좌 목록
    @GetMapping("/list/general-account")
    public Response getGeneralAccountList(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, homeAccountService.getHomeAccountDetail(memberId));
    }
    // 2-2. 사업자계좌 목록
    @GetMapping("/list/business-account")
    public Response getBusinessAccountList(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, businessHomeAccountService.getBusinessAccountDetail(memberId));
    }
    // 2-3. 펫계좌 목록
    @GetMapping("/list/pet-account")
    public Response getPetAccountList(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, petHomeAccountService.getAnimalAccountDetail(memberId));
    }

    // 3. 충전계좌 선택 페이지
    @GetMapping("/charging-account-list")
    public ResponseEntity<?> getChargingAccountList(@RequestHeader("id") Long memberId){ // memberId를 그대로 보낼것인가?
        Response response=new Response(200, GENERAL_SUCCESS.getMessage(),
                accountService.getChargingAccountList(memberId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select-charging-account")
    public ResponseEntity<?> selectChargingAccount(@RequestBody SelectChargingAccountRequest request){
        Response response=new Response(200, GENERAL_SUCCESS.getMessage(),
                accountService.selectChargingAccount(request));
        return ResponseEntity.ok(response);
    }
    
    // 마이페이지 - 접근 가능한 타인의 동물계좌 목록 반환
    @GetMapping("/list/accessible-pet-account")
    public Response getAccessibleAccountList(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, accountService.getAccessibleAccountList(memberId));
    }

    // 카테고리별 전체 소비현황
    @GetMapping("/all-consumption/{accountId}")
    public Response getCategoryExpenditureDetail(@PathVariable Long accountId) {
        return Response.success(GENERAL_SUCCESS, accountService.getCategoryExpenditureDetail(accountId));
    }

    // 이번달 소비현황
    @GetMapping("/this-month-consumption/{accountId}")
    public Response getMonthlyExpenditureDetail(@PathVariable Long accountId) {
        return Response.success(GENERAL_SUCCESS, accountService.getMonthlyExpenditureDetail(accountId));
    }
    
    // 로그인한 사용자의 모든 계좌 목록 반환
    @GetMapping("/list/all-account")
    public Response findAllMemberAccount(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, accountService.findMemberAccount(memberId));
    }

    // 관리자 페이지
    // 1. 전체 사용자의 계좌 목록 반환
    @PostMapping("/admin/list/all-account")
    public Response findMemberAccountForAdmin(@RequestBody AdminAllMemberIdsRequest request) {
        return Response.success(GENERAL_SUCCESS, accountService.findAllMemberAccounts(request));
    }
    
    // 2. 전체 일반계좌 및 동물계좌 수 반환
    @GetMapping("/admin/count/all-account")
    public Response countAllAccountForAdmin() {
        return Response.success(GENERAL_SUCCESS, accountService.countAllAccountForAdmin());
    }

    // 3. 일주일 간 새로 생성된 계좌 개수 반환
    @GetMapping("/admin/count/new-account-in-a-week")
    public Response countNewAccountInWeek() {
        return Response.success(GENERAL_SUCCESS, accountService.countNewAccountInWeek());
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
}
