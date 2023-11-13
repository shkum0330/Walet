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
import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.common.domain.util.EncryptUtil;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.db.repository.TransferRepository;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.TransactionService;
import com.ssafy.account.service.TransferService;
import com.ssafy.account.service.impl.AccountServiceImpl;
import com.ssafy.external.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.DIFFERENT_RFID;
import static com.ssafy.account.common.api.status.FailCode.INVALID_TRANSFER;
import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;
import static com.ssafy.account.db.entity.transfer.Transfer.TransferStatus.PENDING;
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
    private final TimeUtil timeUtil;
    private final EncryptUtil encryptUtil;


    // 양도 요청
    @PostMapping("/transfer/request")
    public Response<?> requestAccountTransfer(@RequestHeader("id") Long ownerId, @RequestHeader("name") String name
            , @RequestBody AccountTransferRequest request){
        Long transferId= transferService.requestAccountTransfer(ownerId, request);
        Account transfereeAccount=accountService.findByAccountNumber(request.getAccountNumber());
        messageSenderService.sendTransferRequestMessage(new AccountTransferNotificationRequest(transfereeAccount.getMemberId(),name));
        return Response.success(GENERAL_SUCCESS,transferId);
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
        Account transferorAccount=accountService.findPetAccountByMemberId(transfer.getTransferorId());
        // response dto 반환
        CheckResponse checkResponse= CheckResponse.builder()
                .petImage(transferorAccount.getPetPhoto())
                .petName(transferorAccount.getPetName())
                .petBreed(transferorAccount.getPetBreed())
                .petGender(transferorAccount.getPetGender())
                .petNeutered(transferorAccount.getPetNeutered() ? "중성화 했어요":"중성화 안했어요")
                .petAge(timeUtil.calculateAge(transferorAccount.getPetBirth())+"살")
                .petBirth(transferorAccount.getPetBirth().getYear()+"년 "+transferorAccount.getPetBirth().getMonth().getValue()+"월생")
                .build();
        NewOwnerResponse newOwnerResponse= NewOwnerResponse.builder()
                .name(oauthService.getUserName(transfereeId))
                .date(timeUtil.transferDateConverter(transfer.getCreatedAt()))
                .content(transfer.getContent())
                .build();
        resultMap.put("newOwnerInfo",newOwnerResponse);
        resultMap.put("petInfo",checkResponse);
        resultMap.put("transferId",transfer.getId());
        return Response.success(GENERAL_SUCCESS, resultMap);
    }
    //  양도신청자 정보 확인

    // 연결할 ACTIVE 상태 계좌 선택
    @GetMapping("/transfer/get-account-info")
    public Response<?> getAccounts(@RequestHeader("id") Long transfereeId){
        List<Account> accountList= accountService.findActiveAccountByMemberId(transfereeId,"00");

        return Response.success(GENERAL_SUCCESS,
                accountList.stream().map(TransferAccountResponse::new).collect(toList()));
    }

    // rfid 인증 후 양도
    @Transactional
    @PostMapping("/transfer/confirm/{transferId}")
    public Response<?> requestAccountTransfer(@PathVariable Long transferId, @RequestBody RFIDAuthRequest request){
        Transfer transfer=transferService.findById(transferId); // 양도 id로 찾는다.
        if(transfer.getStatus() != PENDING){ // 유효한 양도인지 확인한다.
            throw new InvalidTransferException(INVALID_TRANSFER);
        }
        // 본 주인의 계좌
        Account transferorAccount=accountService.findPetAccountByAccountId(transfer.getTransferorId());
        // 양도받을 사람의 계좌
        Account transfereeAccount=accountService.findAccountByAccountId(request.getSenderAccountId());
        if(!encryptUtil.hashPassword(request.getRfidCode()).equals(transferorAccount.getRfidCode())){
            log.info("rfid: {} {}",request.getRfidCode(), encryptUtil.hashPassword(request.getRfidCode()));
            throw new NotCorrectException(DIFFERENT_RFID);
        }
        log.info("양도인 계좌 pk: {}, 양수인 계좌 pk: {}",transferorAccount.getId(),transfereeAccount.getId());
        // 양도 진행. 양도된 금액 반환
        Long amount = accountService.assignAccount(new AssignRequest(transferorAccount.getId(), transfereeAccount.getId()));
        log.info("양도금액: {}",amount);
        // 트랜잭션에 반영
        transactionRepository.save(new Transaction(transferorAccount, transfereeAccount.getDepositorName(), TransactionType.TRANSFER, amount, 0L));
        transactionRepository.save(new Transaction(transfereeAccount, transferorAccount.getDepositorName(), TransactionType.TRANSFER
                , amount, transfereeAccount.getBalance()+amount));
        transfer.completeTransfer();
        return Response.success(GENERAL_SUCCESS, null);

    }


}
