package com.ssafy.service.api;

import com.ssafy.service.external.dto.CheckOpenFinAccountDto;
import com.ssafy.service.external.dto.FinAccountDto;
import com.ssafy.service.external.dto.OpenFinAccountARSDto;
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


    @PostMapping("/InquireFinAccountStatus")
    public ResponseEntity<FinAccountDto.Response> InquireFinAccountStatus(@RequestBody FinAccountDto.Request request){
        return ResponseEntity.ok(nhFintechService.InquireFinAccountStatus(request));
    }
}
