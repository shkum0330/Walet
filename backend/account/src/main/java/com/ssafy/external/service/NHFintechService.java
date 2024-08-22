package com.ssafy.external.service;

import com.ssafy.external.dto.*;

public interface NHFintechService {
    OpenFinAccountARSDto.Response OpenFinAccountARS(OpenFinAccountARSDto.Request request); //핀어카운트 ARS 발급
    CheckOpenFinAccountDto.Response CheckOpenFinAccount(CheckOpenFinAccountDto.Request request); //핀-어카운트 ARS발급 확인
    FinAccountDto.Response CloseFinAccount(FinAccountDto.Request request); //핀-어카운트 해지
    FinAccountDto.Response InquireFinAccountStatus(FinAccountDto.Request request); //핀-어카운트 상태조회
    DrawingTransfer2Dto.Response DrawingTransfer2(DrawingTransfer2Dto.Request request); //실시간 출금이체
    CheckOnDrawingTransfer2Dto.Response CheckOnDrawingTransfer2(CheckOnDrawingTransfer2Dto.Request request); //실시간 출금이체 결과확인
    ReceivedTransferFinAccountDto.Response ReceivedTransferFinAccount(ReceivedTransferFinAccountDto.Request request); //농협입금이체(핀-어카운트)
    CheckOnReceivedTransferDto.Response CheckOnReceivedTransfer(CheckOnReceivedTransferDto.Request request); //입금이체 결과확인
    CheckDepositorFinAccountDto.Response CheckDepositorFinAccount(CheckDepositorFinAccountDto.Request request); //예금주 실명확인(핀-어카운트)
    InquireDepositorFinAccountDto.Response InquireDepositorFinAccount(InquireDepositorFinAccountDto.Request request); //예금주 조회(핀-어카운트)
    InquireBalanceDto.Response InquireBalance(InquireBalanceDto.Request request); //잔액조회
    InquireTransactionHistoryDto.Response InquireTransactionHistory(InquireTransactionHistoryDto.Request request); //거래내역조회
    P2PNVirtualAccountNumberRequestDto.Response P2PNVirtualAccountNumberRequest(P2PNVirtualAccountNumberRequestDto.Request request); //(신규) P2P투자자및차입자 가상계좌발급

    P2PNVirtualAccountTerminateDto.Response P2PNVirtualAccountTerminate(P2PNVirtualAccountTerminateDto.Request request);  //(신규) P2P투자자/차입자 가상계좌 해지
    P2PNInvestmentManagementVirtualAccountListDto.Response P2PNInvestmentManagementVirtualAccountList(P2PNInvestmentManagementVirtualAccountListDto.Request request);
}
