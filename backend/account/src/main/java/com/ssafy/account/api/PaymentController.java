package com.ssafy.account.api;

import com.ssafy.account.api.request.message.PaymentNotificationRequest;
import com.ssafy.account.api.request.payment.PaymentRequest;
import com.ssafy.account.api.request.payment.RFIDAuthRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.payment.CheckResponse;
import com.ssafy.account.api.response.payment.PaymentInfoResponse;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.api.exception.InvalidPaymentException;
import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.PaymentService;
import com.ssafy.account.service.TransactionService;
import com.ssafy.external.service.NHFintechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import static com.ssafy.account.common.api.status.ProcessStatus.*;
import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final AccountService accountService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final MessageSenderService messageSenderService;
    private final NHFintechService fintechService;
    private final TimeUtil timeUtil;
    private final String[] types = {"동물병원","반려동물용품","반려동물미용","애견카페","반려견놀이터"};

    // 판매자가 결제 요청을 보냄
    @PostMapping("/payment/request")
    public Response<?> requestPayment(@RequestHeader("id") Long sellerId,@RequestBody PaymentRequest request){
        request.setSellerId(sellerId);
        Long paymentId=paymentService.requestPayment(request);
        log.info("paymentId: {}",paymentId);
        return Response.ok(GENERAL_SUCCESS, paymentId);
    }
    // rfid 결제
    @PostMapping("/payment/rfid/{paymentId}")
    public Response<?> RFIDPayment(@PathVariable Long paymentId, @RequestBody RFIDAuthRequest request) {
        Payment payment = paymentService.findPayment(paymentId); // 결제 정보를 가져온다.
        if (payment.getStatus() != PENDING) {
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account account=accountService.findByRFID(PasswordEncoder.hashPassword(request.getRfidCode()));
        log.info("buyerId: {}",account.getMemberId());
        paymentService.setBuyer(payment,account.getMemberId());
        CheckResponse response= CheckResponse.builder()
                .petImage(account.getPetPhoto())
                .petName(account.getPetName())
                .petBreed(account.getPetBreed())
                .petGender(account.getPetGender())
                .petNeutered(account.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
                .petAge(timeUtil.calculateAge(account.getPetBirth())+"살")
                .petBirth(account.getPetBirth().getYear()+"년 "+account.getPetBirth().getMonth().getValue()+"월생")
                .build();
        return Response.ok(GENERAL_SUCCESS, response);
    }

    // RFID 인증을 하면 상대에게 푸쉬알림을 보낸다.
    @PostMapping("/payment/message/{paymentId}")
    public Response<?> sendNotification(@RequestHeader("id") Long sellerId,@PathVariable Long paymentId, @RequestBody RFIDAuthRequest request){
        Payment payment=paymentService.findPayment(paymentId); // 결제 정보를 가져온다.
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        log.info("계좌번호: {}", PasswordEncoder.hashPassword(request.getRfidCode()));
        Account sellerAccount=accountService.findBusinessAccountByMemberId(sellerId);
        Account buyerAccount=accountService.findByRFID(PasswordEncoder.hashPassword(request.getRfidCode()));

        messageSenderService.sendPaymentMessage(new PaymentNotificationRequest(
                buyerAccount.getMemberId(), payment.getPaymentAmount(), sellerAccount.getDepositorName()
        ));
        return Response.ok(GENERAL_SUCCESS, "알림 전송");
    }

    // 결제 정보를 출력한다.
    @GetMapping("/payment/info")
    public Response<?> getPaymentInfo(@RequestHeader("id") Long buyerId){
        Payment payment=paymentService.findByBuyerId(buyerId);
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account sellerAccount=accountService.findBusinessAccountByMemberId(payment.getSellerId());
        PaymentInfoResponse response=new PaymentInfoResponse(sellerAccount.getDepositorName()
                ,types[sellerAccount.getBusinessType()-1],
                null,timeUtil.transferDateTimeConverter(LocalDateTime.now()), payment.getId(), payment.getPaymentAmount());
        return Response.ok(GENERAL_SUCCESS, response);
    }

    @PostMapping("/payment/complete/{paymentId}")
    public Response<?> completePayment(@RequestHeader("id") Long buyerId, @PathVariable Long paymentId) {
        Payment payment=paymentService.findPayment(paymentId);
        ConcurrentHashMap<String,Long> resultMap=new ConcurrentHashMap<>();
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account buyerAccount=accountService.findPetAccountByMemberId(buyerId);
        Account sellerAccount=accountService.findBusinessAccountByMemberId(payment.getSellerId());
        Long transactionId=transactionService.addPetRelatedTransaction(new TransactionRequest(buyerAccount.getMemberId()
                ,sellerAccount.getMemberId(),payment.getPaymentAmount()));

        // 결제 상태를 완료로 변경
        paymentService.completePayment(paymentService.findPayment(paymentId));

        resultMap.put("paymentId",paymentId);
        resultMap.put("transactionId",transactionId);
        return Response.ok(GENERAL_SUCCESS, resultMap);

    }
}
