package com.example.account.api;

import com.example.account.api.request.payment.AuthRequest;
import com.example.account.api.request.payment.PaymentRequest;
import com.example.account.common.api.Response;
import com.example.account.common.api.status.SuccessCode;
import com.example.account.service.AccountService;
import com.example.account.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final AccountService accountService;
    private final PaymentService paymentService;

    // 판매자가 결제 요청을 보냄
    @PostMapping("/payment/request")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentRequest request){

        paymentService.requestPayment(request);
        return ResponseEntity.ok(new Response<>(200, SuccessCode.GENERAL_SUCCESS.getMessage()));
    }

    // 사용자가 인증을 하고 결제를 실행한다.
    @PostMapping("/payment/auth")
    public ResponseEntity<?> authAndPayment(AuthRequest request){


        return ResponseEntity.ok(null);
    }
}
