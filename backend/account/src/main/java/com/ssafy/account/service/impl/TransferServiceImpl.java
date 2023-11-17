package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransferRepository;
import com.ssafy.account.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.account.common.api.status.FailCode.NO_ACCOUNT;
import static com.ssafy.account.common.api.status.FailCode.NO_TRANSFER;
import static com.ssafy.account.common.api.status.ProcessStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Override
    @Transactional
    public Long requestAccountTransfer(Long ownerId, AccountTransferRequest request) {

        Transfer transfer=new Transfer(request.getContent(),PENDING);
        transfer.setTransferorId(ownerId);
        if(transferRepository.findByTransferorIdAndStatus(ownerId,PENDING) != null){ // 이미 진행중인 양도가 있다면
            log.info("삭제 실행 {}",ownerId);
            transferRepository.deleteByTransferorId(ownerId); // 기존의 양도요청은 삭제한다.
        }
        transferRepository.save(transfer);
        Account transfereeAccount=accountRepository.findByDepositorNameAndAccountNumber(request.getNewOwner(),request.getAccountNumber()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        transfer.setTransfereeId(transfereeAccount.getMemberId());
        return transfer.getId();
    }

    @Override
    public Transfer findByTransfereeId(Long transfereeId) {
        return transferRepository.findByTransfereeId(transfereeId).orElseThrow(() -> new NotFoundException(NO_TRANSFER));
    }

    @Override
    public Transfer findById(Long transferId) {
        return transferRepository.findById(transferId).orElseThrow(() -> new NotFoundException(NO_TRANSFER));
    }
}
