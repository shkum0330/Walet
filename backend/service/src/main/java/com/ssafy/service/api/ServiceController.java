package com.ssafy.service.api;

import com.ssafy.service.external.dto.*;
import com.ssafy.service.external.service.NHFintechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final NHFintechService nhFintechService;
    
//    핀어카운트 발급
    @PostMapping("/OpenFinAccountARS")
    public ResponseEntity<OpenFinAccountARSDto.Response> OpenFinAccountARS(@RequestBody OpenFinAccountARSDto.Request request){
        return ResponseEntity.ok(nhFintechService.OpenFinAccountARS(request));
    }

//    핀어카운트 발급 확인 
    @PostMapping("/CheckOpenFinAccount")
    public ResponseEntity<CheckOpenFinAccountDto.Response> CheckOpenFinAccount(@RequestBody CheckOpenFinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.CheckOpenFinAccount(request));
    }
//    핀어카운트 해제
    @PostMapping("/CloseFinAccount")
    public ResponseEntity<FinAccountDto.Response> CloseFinAccount(@RequestBody FinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.CloseFinAccount(request));
    }

// 핀어카운트 상태확인
    @PostMapping("/InquireFinAccountStatus")
    public ResponseEntity<FinAccountDto.Response> InquireFinAccountStatus(@RequestBody FinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.InquireFinAccountStatus(request));
    }

//    출금이체
    @PostMapping("/DrawingTransfer2")
    public ResponseEntity<DrawingTransfer2Dto.Response> DrawingTransfer2(@RequestBody DrawingTransfer2Dto.Request request){
        return ResponseEntity.ok(nhFintechService.DrawingTransfer2(request));
    }
    
//    출금이체 결과확인
    @PostMapping("/CheckOnDrawingTransfer2")
    public ResponseEntity<CheckOnDrawingTransfer2Dto.Response> CheckOnDrawingTransfer2(@RequestBody CheckOnDrawingTransfer2Dto.Request request){
        return ResponseEntity.ok(nhFintechService.CheckOnDrawingTransfer2(request));
    }

//    임금이체
    @PostMapping("/ReceivedTransferFinAccount")
    public ResponseEntity<ReceivedTransferFinAccountDto.Response> ReceivedTransferFinAccount(@RequestBody ReceivedTransferFinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.ReceivedTransferFinAccount(request));
    }

//    입금이체 확인
    @PostMapping("/CheckOnReceivedTransfer")
    public ResponseEntity<CheckOnReceivedTransferDto.Response> CheckOnReceivedTransfer(@RequestBody CheckOnReceivedTransferDto.Request request){
        return ResponseEntity.ok(nhFintechService.CheckOnReceivedTransfer(request));
    }

//    예금주 실명확인
    @PostMapping("/CheckDepositorFinAccount")
    public ResponseEntity<CheckDepositorFinAccountDto.Response> CheckDepositorFinAccount(@RequestBody CheckDepositorFinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.CheckDepositorFinAccount(request));
    }

//    예금주 조회
    @PostMapping("/InquireDepositorFinAccount")
    public ResponseEntity<InquireDepositorFinAccountDto.Response> InquireDepositorFinAccount(@RequestBody InquireDepositorFinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.InquireDepositorFinAccount(request));
    }
//   잔액조회
    @PostMapping("/InquireBalance")
    public ResponseEntity<InquireBalanceDto.Response> InquireBalance(@RequestBody InquireBalanceDto.Request request){
        return ResponseEntity.ok(nhFintechService.InquireBalance(request));
    }

//    거래내역조회
    @PostMapping("/InquireTransactionHistory")
    public ResponseEntity<InquireTransactionHistoryDto.Response> InquireTransactionHistory(@RequestBody InquireTransactionHistoryDto.Request request){
        return ResponseEntity.ok(nhFintechService.InquireTransactionHistory(request));
    }

//    투자자 및 차입자 가상계좌 발급
    @PostMapping("/P2PNVirtualAccountNumberRequest")
    public ResponseEntity<P2PNVirtualAccountNumberRequestDto.Response> P2PNVirtualAccountNumberRequest(@RequestBody P2PNVirtualAccountNumberRequestDto.Request request){
        return ResponseEntity.ok(nhFintechService.P2PNVirtualAccountNumberRequest(request));
    }

//    투자자 및 차입자 가상계좌 해지
    @PostMapping("/P2PNVirtualAccountTerminate")
    public ResponseEntity<P2PNVirtualAccountTerminateDto.Response> P2PNVirtualAccountNumberRequest(@RequestBody P2PNVirtualAccountTerminateDto.Request request){
        return ResponseEntity.ok(nhFintechService.P2PNVirtualAccountTerminate(request));
    }

    

}
