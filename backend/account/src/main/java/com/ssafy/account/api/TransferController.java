package com.ssafy.account.api;

import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.api.response.account.transfer.NewOwnerResponse;
import com.ssafy.account.api.response.payment.CheckResponse;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.db.repository.TransferRepository;
import com.ssafy.account.service.AccountService;
import com.ssafy.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransferController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransferRepository transferRepository;
    private final TimeUtil timeUtil;

    // 양도 요청
    @PostMapping("/transfer/request")
    public Response<?> requestAccountTransfer(@RequestHeader("owner-id") Long ownerId
            , @RequestBody AccountTransferRequest request){
        Map<String,Object> resultMap=new ConcurrentHashMap<>();
        Transfer transfer=new Transfer(Transfer.TransferStatus.PENDING);
        transfer.setTransferorId(ownerId);
        transferRepository.save(transfer);
        Account transferorAccount=accountService.findPetAccountByAccountId(ownerId);

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
                .name(request.getNewOwner())
                .date(timeUtil.transferDateConverter(LocalDateTime.now()))
                .build();
        resultMap.put("newOwnerInfo",newOwnerResponse);
        resultMap.put("petInfo",checkResponse);

        return Response.success(GENERAL_SUCCESS, resultMap);
    }

    // 연결할 계좌 선택?


    // rfid 인증 후 양도
    @PostMapping("/transfer/confrim")
    public Response<?> requestAccountTransfer(@RequestHeader("id") Long ownerId){

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
