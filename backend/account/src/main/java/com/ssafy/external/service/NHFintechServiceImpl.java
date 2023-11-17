package com.ssafy.external.service;

import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.external.client.NHFintechClient;
import com.ssafy.external.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.account.common.api.status.FailCode.NO_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NHFintechServiceImpl implements NHFintechService{
    private final NHFintechClient nhFintechClient;
    private final AccountRepository accountRepository;

    @Override
    public OpenFinAccountARSDto.Response OpenFinAccountARS(OpenFinAccountARSDto.Request request) {
        return nhFintechClient.OpenFinAccountARS(request);
    }

    @Override
    public CheckOpenFinAccountDto.Response CheckOpenFinAccount(CheckOpenFinAccountDto.Request request) {
        return nhFintechClient.CheckOpenFinAccount(request);
    }

    @Override
    public FinAccountDto.Response CloseFinAccount(FinAccountDto.Request request) {
        return nhFintechClient.CloseFinAccount(request);
    }

    @Override
    public FinAccountDto.Response InquireFinAccountStatus(FinAccountDto.Request request) {
        return nhFintechClient.InquireFinAccountStatus(request);
    }

    @Override
    public DrawingTransfer2Dto.Response DrawingTransfer2(DrawingTransfer2Dto.Request request) {
        return nhFintechClient.DrawingTransfer2(request);
    }

    @Override
    public CheckOnDrawingTransfer2Dto.Response CheckOnDrawingTransfer2(CheckOnDrawingTransfer2Dto.Request request) {
        return nhFintechClient.CheckOnDrawingTransfer2(request);
    }

    @Override
    public ReceivedTransferFinAccountDto.Response ReceivedTransferFinAccount(ReceivedTransferFinAccountDto.Request request) {
        return nhFintechClient.ReceivedTransferFinAccount(request);
    }

    @Override
    public CheckOnReceivedTransferDto.Response CheckOnReceivedTransfer(CheckOnReceivedTransferDto.Request request) {
        return nhFintechClient.CheckOnReceivedTransfer(request);
    }

    @Override
    public CheckDepositorFinAccountDto.Response CheckDepositorFinAccount(CheckDepositorFinAccountDto.Request request) {
        return nhFintechClient.CheckDepositorFinAccount(request);
    }

    @Override
    public InquireDepositorFinAccountDto.Response InquireDepositorFinAccount(InquireDepositorFinAccountDto.Request request) {
        return nhFintechClient.InquireDepositorFinAccount(request);
    }

    @Override
    public InquireBalanceDto.Response InquireBalance(InquireBalanceDto.Request request) {
        return nhFintechClient.InquireBalance(request);
    }

    @Override
    public InquireTransactionHistoryDto.Response InquireTransactionHistory(InquireTransactionHistoryDto.Request request) {
        return nhFintechClient.InquireTransactionHistory(request);
    }

    @Override
    public P2PNVirtualAccountNumberRequestDto.Response P2PNVirtualAccountNumberRequest(P2PNVirtualAccountNumberRequestDto.Request request) {
        return nhFintechClient.P2PNVirtualAccountNumberRequest(request);
    }

    @Override
    public P2PNVirtualAccountTerminateDto.Response P2PNVirtualAccountTerminate(P2PNVirtualAccountTerminateDto.Request request) {
        return nhFintechClient.P2PNVirtualAccountTerminate(request);
    }

    @Override
    public P2PNInvestmentManagementVirtualAccountListDto.Response P2PNInvestmentManagementVirtualAccountList(P2PNInvestmentManagementVirtualAccountListDto.Request request) {
        return nhFintechClient.P2PNInvestmentManagementVirtualAccountList(request);
    }


    // 농협 api를 이용한 송금
    public void remittance(Account senderAccount,Account receiverAccount,Long amount){

        String senderPinAccount=senderAccount.getPinAccount();
        String senderVirtualAccount=senderAccount.getVirtualAccount();
        String receiverPinAccount=receiverAccount.getPinAccount();
        // 출금이체
        DrawingTransfer2(new DrawingTransfer2Dto.Request(senderPinAccount,String.valueOf(amount),senderVirtualAccount,"출금인자","입금인자"));
        // 입금이체
        ReceivedTransferFinAccount(new ReceivedTransferFinAccountDto.Request(receiverPinAccount,String.valueOf(amount),"출금인자","입금인자"));

    }
}
