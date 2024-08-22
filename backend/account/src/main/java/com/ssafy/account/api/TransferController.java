package com.ssafy.account.api;

import com.ssafy.account.api.request.account.AssignRequest;
import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.api.request.message.AccountTransferNotificationRequest;
import com.ssafy.account.api.request.payment.RFIDAuthRequest;
import com.ssafy.account.api.response.account.TransferAccountResponse;
import com.ssafy.account.api.response.account.transfer.NewOwnerResponse;
import com.ssafy.account.api.response.payment.CheckResponse;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.api.exception.InvalidTransferException;
import com.ssafy.account.common.api.exception.NotCorrectException;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.impl.TransactionService;
import com.ssafy.account.service.impl.TransferService;
import com.ssafy.external.service.NHFintechService;
import com.ssafy.external.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ssafy.account.common.api.status.FailCode.DIFFERENT_RFID;
import static com.ssafy.account.common.api.status.FailCode.INVALID_TRANSFER;
import static com.ssafy.account.common.api.status.ProcessStatus.PENDING;
import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;
import static java.util.stream.Collectors.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransferController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransferService transferService;
    private final MessageSenderService messageSenderService;
    private final OauthService oauthService;
    private final NHFintechService nhFintechService;
    private final TimeUtil timeUtil;


    // 양도 요청
    @PostMapping("/transfer/request")
    public Response<?> requestAccountTransfer(@RequestHeader("id") Long ownerId, @RequestHeader("name") String name
            , @RequestBody AccountTransferRequest request){
        Long transferId= transferService.requestAccountTransfer(ownerId, request);
        PetAccount transfereePetAccount =accountService.findByAccountNumber(request.getAccountNumber());
        messageSenderService.sendTransferRequestMessage(new AccountTransferNotificationRequest(transfereePetAccount.getMemberId(),name));
        return Response.ok(GENERAL_SUCCESS,transferId);
    }

    // 알림 누르면 해당 페이지 출력
    @GetMapping("/transfer/get-info")
    public Response<?> getTransferInfo(@RequestHeader("id") Long transfereeId){
        Map<String,Object> resultMap=new ConcurrentHashMap<>();
        Transfer transfer=transferService.findByTransfereeId(transfereeId);
        if(transfer.getStatus() != PENDING){ // 반드시 대기 상태여야 함
            throw new InvalidTransferException(INVALID_TRANSFER);
        }
        log.info("양도자 id: {}",transfer.getTransferorId());
        PetAccount transferorPetAccount =accountService.findPetAccountByMemberId(transfer.getTransferorId());
        // response dto 반환
        CheckResponse checkResponse= CheckResponse.builder()
                .petImage(transferorPetAccount.getPetPhoto())
                .petName(transferorPetAccount.getPetName())
                .petBreed(transferorPetAccount.getPetBreed())
                .petGender(transferorPetAccount.getPetGender())
                .petNeutered(transferorPetAccount.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
                .petAge(timeUtil.calculateAge(transferorPetAccount.getPetBirth())+"살")
                .petBirth(transferorPetAccount.getPetBirth().getYear()+"년 "+ transferorPetAccount.getPetBirth().getMonth().getValue()+"월생")
                .build();
        NewOwnerResponse newOwnerResponse= NewOwnerResponse.builder()
                .name(oauthService.getUserName(transfereeId))
                .date(timeUtil.transferDateConverter(transfer.getCreatedAt()))
                .content(transfer.getContent())
                .build();
        resultMap.put("newOwnerInfo",newOwnerResponse);
        resultMap.put("petInfo",checkResponse);
        resultMap.put("transferId",transfer.getId());
        return Response.ok(GENERAL_SUCCESS, resultMap);
    }
    //  양도신청자 정보 확인

    // 연결할 ACTIVE 상태 계좌 선택
    @GetMapping("/transfer/get-account-info")
    public Response<?> getAccounts(@RequestHeader("id") Long transfereeId){
        List<PetAccount> petAccountList = accountService.findActiveAccountByMemberId(transfereeId,"00");

        return Response.ok(GENERAL_SUCCESS,
                petAccountList.stream().map(TransferAccountResponse::new).collect(toList()));
    }

    // rfid 인증 후 양도
    @PostMapping("/transfer/confirm/{transferId}")
    public Response<?> requestAccountTransfer(@PathVariable Long transferId, @RequestBody RFIDAuthRequest request){
        Transfer transfer=transferService.findById(transferId); // 양도 id로 찾는다.
        if(transfer.getStatus() != PENDING){ // 유효한 양도인지 확인한다.
            throw new InvalidTransferException(INVALID_TRANSFER);
        }
        // 본 주인의 계좌
        PetAccount transferorPetAccount =accountService.findPetAccountByAccountId(transfer.getTransferorId());
        // 양도받을 사람의 계좌
        PetAccount transfereePetAccount =accountService.findActiveAccountByMemberId(transfer.getTransfereeId(),"00").get(0);
        if(!PasswordEncoder.hashPassword(request.getRfidCode()).equals(transferorPetAccount.getRfidCode())){
            log.info("rfid: {} {}",request.getRfidCode(), PasswordEncoder.hashPassword(request.getRfidCode()));
            throw new NotCorrectException(DIFFERENT_RFID);
        }
        log.info("양도인 계좌 pk: {}, 양수인 계좌 pk: {}", transferorPetAccount.getId(), transfereePetAccount.getId());
        // 양도 진행. 양도된 금액 반환
        Long amount = accountService.assignAccount(new AssignRequest(transferorPetAccount.getId(), transfereePetAccount.getId()));
        log.info("양도금액: {}",amount);
        // 농협 api로 송금 진행
//        nhFintechService.remittance(transferorAccount,transfereeAccount,amount);
        // 트랜잭션에 반영
        transactionRepository.save(new Transaction(transferorPetAccount, transfereePetAccount.getDepositorName(), TransactionType.TRANSFER, amount, 0L));
        transactionRepository.save(new Transaction(transfereePetAccount, transferorPetAccount.getDepositorName(), TransactionType.TRANSFER
                , amount, transfereePetAccount.getBalance()+amount));
        transfer.completeTransfer();
        return Response.ok(GENERAL_SUCCESS, "ok");

    }


}
