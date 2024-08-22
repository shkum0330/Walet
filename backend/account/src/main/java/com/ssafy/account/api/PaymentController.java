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
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.impl.AccountService;
import com.ssafy.account.service.impl.PaymentService;
import com.ssafy.account.service.impl.TransactionService;
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
        PetAccount petAccount =accountService.findByRFID(PasswordEncoder.hashPassword(request.getRfidCode()));
        log.info("buyerId: {}", petAccount.getMemberId());
        paymentService.setBuyer(payment, petAccount.getMemberId());
        CheckResponse response= CheckResponse.builder()
                .petImage(petAccount.getPetPhoto())
                .petName(petAccount.getPetName())
                .petBreed(petAccount.getPetBreed())
                .petGender(petAccount.getPetGender())
                .petNeutered(petAccount.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
                .petAge(timeUtil.calculateAge(petAccount.getPetBirth())+"살")
                .petBirth(petAccount.getPetBirth().getYear()+"년 "+ petAccount.getPetBirth().getMonth().getValue()+"월생")
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
        PetAccount sellerPetAccount =accountService.findBusinessAccountByMemberId(sellerId);
        PetAccount buyerPetAccount =accountService.findByRFID(PasswordEncoder.hashPassword(request.getRfidCode()));

        messageSenderService.sendPaymentMessage(new PaymentNotificationRequest(
                buyerPetAccount.getMemberId(), payment.getPaymentAmount(), sellerPetAccount.getDepositorName()
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
        PetAccount sellerPetAccount =accountService.findBusinessAccountByMemberId(payment.getSellerId());
        PaymentInfoResponse response=new PaymentInfoResponse(sellerPetAccount.getDepositorName()
                ,types[sellerPetAccount.getBusinessType()-1],
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
        PetAccount buyerPetAccount =accountService.findPetAccountByMemberId(buyerId);
        PetAccount sellerPetAccount =accountService.findBusinessAccountByMemberId(payment.getSellerId());
        Long transactionId=transactionService.addPetRelatedTransaction(new TransactionRequest(buyerPetAccount.getMemberId()
                , sellerPetAccount.getMemberId(),payment.getPaymentAmount()));

        // 결제 상태를 완료로 변경
        paymentService.completePayment(paymentService.findPayment(paymentId));

        resultMap.put("paymentId",paymentId);
        resultMap.put("transactionId",transactionId);
        return Response.ok(GENERAL_SUCCESS, resultMap);

    }
}
