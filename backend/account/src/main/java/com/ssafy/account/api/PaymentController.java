package com.ssafy.account.api;

import com.ssafy.account.api.request.message.PaymentNotificationRequest;
import com.ssafy.account.api.request.payment.PaymentRequest;
import com.ssafy.account.api.request.payment.RFIDAuthRequest;
import com.ssafy.account.api.response.payment.CheckResponse;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.api.exception.InvalidPaymentException;
import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.common.api.status.SuccessCode;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.PaymentService;
import com.ssafy.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.account.common.api.status.ProcessStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final AccountService accountService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final MessageSenderService messageSenderService;
    private final TimeUtil timeUtil;

    // 판매자가 결제 요청을 보냄
    @PostMapping("/payment/request")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentRequest request){

        Long paymentId=paymentService.requestPayment(request);
        log.info("paymentId: {}",paymentId);
        return ResponseEntity.ok(new Response<>(200, SuccessCode.GENERAL_SUCCESS.getMessage(),paymentId));
    }

    // rfid 결제
    @PostMapping("/payment/rfid/{paymentId}")
    public ResponseEntity<?> RFIDPayment(@PathVariable Long paymentId, @RequestBody RFIDAuthRequest request){
        Payment payment=paymentService.findPayment(paymentId); // 결제 정보를 가져온다.
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account account=accountService.findPetAccountByAccountId(request.getSenderId());
//        transactionService.addPetRelatedTransaction(new TransactionRequest(request.getRfidCode(), request.getBuyerId(),
//                payment.getSellerId(), TransactionType.WITHDRAWAL,payment.getPaymentAmount()));
        paymentService.completePayment(payment);

        CheckResponse response= CheckResponse.builder()
                .petImage(account.getPetPhoto())
                .petName(account.getPetName())
                .petBreed(account.getPetBreed())
                .petGender(account.getPetGender())
                .petNeutered(account.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
                .petAge(timeUtil.calculateAge(account.getPetBirth())+"살")
                .petBirth(account.getPetBirth().getYear()+"년 "+account.getPetBirth().getMonth().getValue()+"월생")
                .build();
        return ResponseEntity.ok(new Response<>(200, SuccessCode.GENERAL_SUCCESS.getMessage(),response));
    }

    // 결제 확정
    @PostMapping("/payment/complete/{paymentId}")
    public String completePayment(@PathVariable Long paymentId) {

        // 결제 상태를 완료로 변경
        paymentService.completePayment(paymentService.findPayment(paymentId));
        // 거래 기록을 db에 저장

        // 알림 보내기
        messageSenderService.sendPaymentMessage(new PaymentNotificationRequest(1L,10000,"이마트"));
        return "message sending!";
    }
}
