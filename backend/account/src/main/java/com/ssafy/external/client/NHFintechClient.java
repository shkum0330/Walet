package com.ssafy.external.client;

import com.ssafy.account.config.ClientProxyConfig;
import com.ssafy.external.dto.CheckOpenFinAccountDto;
import com.ssafy.external.dto.FinAccountDto;
import com.ssafy.external.dto.OpenFinAccountARSDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://smartdev.nonghyup.com:9460/svcapi" , name = "Server1" , configuration = ClientProxyConfig.class)
public interface NHFintechClient {
    @PostMapping(value = "/OpenFinAccountARS.nhd" , headers = "ApiNm=OpenFinAccountARS")
    OpenFinAccountARSDto.Response OpenFinAccountARS(@RequestBody OpenFinAccountARSDto.Request request);

    @PostMapping(value = "/CheckOpenFinAccount.nhd" , headers = "ApiNm=CheckOpenFinAccount")
    CheckOpenFinAccountDto.Response CheckOpenFinAccount(@RequestBody CheckOpenFinAccountDto.Request request);

    @PostMapping(value = "/CloseFinAccount.nhd" , headers = "ApiNm=CloseFinAccount")
    void CloseFinAccount(@RequestBody FinAccountDto.Request request);

    @PostMapping(value = "/InquireFinAccountStatus.nhd" , headers = "ApiNm=InquireFinAccountStatus")
    FinAccountDto.Response InquireFinAccountStatus(@RequestBody FinAccountDto.Request request);

    @PostMapping(value = "/P2PNVirtualAccountNumberRequest.nhd" , headers = "ApiNm=P2PNVirtualAccountNumberRequest")
    String P2PNVirtualAccountNumberRequest();

    @PostMapping(value = "/P2PNVirtualAccountTerminate.nhd" , headers = "ApiNm=P2PNVirtualAccountTerminate")
    String P2PNVirtualAccountTerminate();

    @PostMapping(value = "/InquireTransactionHistory.nhd" , headers = "ApiNm=InquireTransactionHistory")
    String InquireTransactionHistory();

    @PostMapping(value = "/InquireBalance.nhd" , headers = "ApiNm=InquireBalance")
    String InquireBalance();

    @PostMapping(value = "/InquireDepositorFinAccount.nhd" , headers = "ApiNm=InquireDepositorFinAccount")
    String InquireDepositorFinAccount();

    @PostMapping(value = "/CheckDepositorFinAccount.nhd" , headers = "ApiNm=CheckDepositorFinAccount")
    String CheckDepositorFinAccount();

    @PostMapping(value = "/DrawingTransfer2.nhd" , headers = "ApiNm=DrawingTransfer2")
    String DrawingTransfer2();

    @PostMapping(value = "/CheckOnDrawingTransfer2.nhd" , headers = "ApiNm=CheckOnDrawingTransfer2")
    String CheckOnDrawingTransfer2();

    @PostMapping(value = "/ReceivedTransferFinAccount.nhd" , headers = "ApiNm=ReceivedTransferFinAccount")
    String ReceivedTransferFinAccount();

    @PostMapping(value = "/CheckOnReceivedTransfer.nhd" , headers = "ApiNm=CheckOnReceivedTransfer")
    String CheckOnReceivedTransfer();
}
