package com.ssafy.account.api;

import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.api.request.message.AccountTransferNotificationRequest;
import com.ssafy.account.api.request.payment.RFIDAuthRequest;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.TransactionService;
import com.ssafy.account.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransferController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransferService transferService;
    private final MessageSenderService messageSenderService;
    private final TimeUtil timeUtil;


    // 양도 요청
    @PostMapping("/transfer/request")
    public Response<?> requestAccountTransfer(@RequestHeader("owner-id") Long ownerId
            , @RequestBody AccountTransferRequest request){
        Long transferId= transferService.requestAccountTransfer(ownerId, request.getAccountNumber());
        messageSenderService.sendTransferRequestMessage(new AccountTransferNotificationRequest(1L,"김민수"));
        return Response.success(GENERAL_SUCCESS,transferId );
    }

    // 양도신청자 정보 확인
//    @PostMapping("/transfer/check")
//    public Response<?> checkTransferInfo(@RequestHeader("owner-id") Long ownerId
//            , @RequestBody AccountTransferRequest request){
//        Map<String,Object> resultMap=new ConcurrentHashMap<>();
//
//        // response dto 반환
//        CheckResponse checkResponse= CheckResponse.builder()
//                .petImage(transferorAccount.getPetPhoto())
//                .petName(transferorAccount.getPetName())
//                .petBreed(transferorAccount.getPetBreed())
//                .petGender(transferorAccount.getPetGender())
//                .petNeutered(transferorAccount.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
//                .petAge(timeUtil.calculateAge(transferorAccount.getPetBirth())+"살")
//                .petBirth(transferorAccount.getPetBirth().getYear()+"년 "+transferorAccount.getPetBirth().getMonth().getValue()+"월생")
//                .build();
//        NewOwnerResponse newOwnerResponse= NewOwnerResponse.builder()
//                .name(request.getNewOwner())
//                .date(timeUtil.transferDateConverter(LocalDateTime.now()))
//                .content(request.getContent())
//                .build();
//        resultMap.put("newOwnerInfo",newOwnerResponse);
//        resultMap.put("petInfo",checkResponse);
//        return Response.success(GENERAL_SUCCESS, resultMap);
//    }

    // 연결할 계좌 선택?

    // rfid 인증 후 양도
    @PostMapping("/transfer/confirm/{transferId}")
    public Response<?> requestAccountTransfer(@PathVariable Long transferId, @RequestBody RFIDAuthRequest request){

//        // 본 주인의 계좌
//        Account transferorAccount=accountService.findPetAccountByAccountId(ownerId);
//        // 양도받을 사람의 계좌
//        Account transfereeAccount=accountService.findPetAccountByDepositorName(request.getNewOwner());
//        // 양도 진행. 양도된 금액 반환
//        Long amount = accountService.assignAccount(new AssignRequest(ownerId, transfereeAccount.getId()));

        // 트랜잭션에 반영
        return Response.success(GENERAL_SUCCESS, null);

    }

}
