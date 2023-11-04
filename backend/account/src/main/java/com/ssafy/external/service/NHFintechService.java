package com.ssafy.external.service;

public interface NHFintechService {
    void OpenFinAccountARS(); //핀어카운트 ARS 발급
    void CheckOpenFinAccount(); //핀-어카운트 ARS발급 확인
    void CloseFinAccount(); //핀-어카운트 해지
    void InquireFinAccountStatus(); //핀-어카운트 상태조회
    void DrawingTransfer2(); //실시간 출금이체
    void CheckOnDrawingTransfer2(); //실시간 출금이체 결과확인
    void ReceivedTransferFinAccount(); //농협입금이체(핀-어카운트)
    void CheckOnReceivedTransfer(); //입금이체 결과확인
    void CheckDepositorFinAccount(); //예금주 실명확인(핀-어카운트)
    void InquireDepositorFinAccount(); //예금주 조회(핀-어카운트)
    void InquireBalance(); //잔액조회
    void InquireTransactionHistory(); //거래내역조회
    void P2PNVirtualAccountNumberRequest(); //(신규) P2P투자자및차입자 가상계좌발급
    void P2PNVirtualAccountTerminate(); //(신규) P2P투자자/차입자 가상계좌 해지
}
