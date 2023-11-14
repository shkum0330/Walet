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
import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.common.api.status.SuccessCode;
import com.ssafy.account.common.domain.util.EncryptUtil;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.PaymentService;
import com.ssafy.account.service.TransactionService;
import com.ssafy.external.service.NHFintechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    private final EncryptUtil encryptUtil;
    private final String[] types = {"동물병원","반려동물용품","반려동물미용","애견카페","반려견놀이터"};
    // 판매자가 결제 요청을 보냄
    @PostMapping("/payment/request")
    public Response<?> requestPayment(@RequestHeader("id") Long sellerId,@RequestBody PaymentRequest request){
        request.setSellerId(sellerId);
        Long paymentId=paymentService.requestPayment(request);
        log.info("paymentId: {}",paymentId);
        return Response.success(GENERAL_SUCCESS, paymentId);
    }

    // rfid 결제
    @PostMapping("/payment/rfid/{paymentId}")
    public Response<?> RFIDPayment(@PathVariable Long paymentId, @RequestBody RFIDAuthRequest request){
        Payment payment=paymentService.findPayment(paymentId); // 결제 정보를 가져온다.
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account account=accountService.findByRFID(encryptUtil.hashPassword(request.getRfidCode()));
//        transactionService.addPetRelatedTransaction(new TransactionRequest(request.getRfidCode(), request.getBuyerId(),
//                payment.getSellerId(), TransactionType.WITHDRAWAL,payment.getPaymentAmount()));

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
        return Response.success(GENERAL_SUCCESS, response);
    }

    // RFID 인증을 하면 상대에게 푸쉬알림을 보낸다.
    @PostMapping("/payment/message/{paymentId}")
    public Response<?> sendNotification(@RequestHeader("id") Long sellerId,@PathVariable Long paymentId, @RequestBody RFIDAuthRequest request){
        Payment payment=paymentService.findPayment(paymentId); // 결제 정보를 가져온다.
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account sellerAccount=accountService.findBusinessAccountByMemberId(sellerId);
        Account buyerAccount=accountService.findByRFID(encryptUtil.hashPassword(request.getRfidCode()));

        messageSenderService.sendPaymentMessage(new PaymentNotificationRequest(
                buyerAccount.getMemberId(), payment.getPaymentAmount(), sellerAccount.getDepositorName()
        ));
        return Response.success(GENERAL_SUCCESS, null);
    }

    //  1. 동물병원
    //  2. 반려동물용품
    //  3. 반려동물미용
    //  4. 애견카페
    //  5. 반려견놀이터

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
                null,timeUtil.transferDateTimeConverter(LocalDateTime.now()), payment.getId());
        return Response.success(GENERAL_SUCCESS, response);
    }


    // 결제 확정
    @PostMapping("/payment/complete/{paymentId}")
    public Response<?> completePayment(@RequestHeader("id") Long buyerId, @PathVariable Long paymentId) {
        Payment payment=paymentService.findPayment(paymentId);
        if(payment.getStatus() != PENDING){
            throw new InvalidPaymentException(FailCode.INVALID_PAYMENT);
        }
        Account buyerAccount=accountService.findPetAccountByMemberId(buyerId);
        Account sellerAccount=accountService.findBusinessAccountByMemberId(payment.getSellerId());
        transactionService.addPetRelatedTransaction(new TransactionRequest(buyerAccount.getMemberId()
                ,sellerAccount.getMemberId(),payment.getPaymentAmount()));

        // 결제 상태를 완료로 변경
        paymentService.completePayment(paymentService.findPayment(paymentId));
        // 거래 기록을 db에 저장

        // 알림 보내기
//        messageSenderService.sendPaymentMessage(new PaymentNotificationRequest(1L,10000,"이마트"));
        return Response.success(GENERAL_SUCCESS, paymentId);
    }
}
