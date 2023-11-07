package com.ssafy.service.external.client;

import com.ssafy.service.config.ClientProxyConfig;
import com.ssafy.service.external.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://smartdev.nonghyup.com:9460/svcapi" , name = "Server1" , configuration = ClientProxyConfig.class)
public interface NHFintechClient {
    @PostMapping(value = "/OpenFinAccountARS.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String OpenFinAccountARS(
            @RequestHeader("Auth") String key,
            @RequestBody OpenFinAccountARSDto.Request data);

    @PostMapping(value = "/CheckOpenFinAccount.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String CheckOpenFinAccount(
            @RequestHeader("Auth") String key,
            @RequestBody CheckOpenFinAccountDto.Request data);

    @PostMapping(value = "/CloseFinAccount.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String CloseFinAccount(
            @RequestHeader("Auth") String key,
            @RequestBody FinAccountDto.Request data);

    @PostMapping(value = "/InquireFinAccountStatus.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String InquireFinAccountStatus(
            @RequestHeader("Auth") String key,
            @RequestBody FinAccountDto.Request request);

    @PostMapping(value = "/DrawingTransfer2.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String DrawingTransfer2(
            @RequestHeader("Auth") String key,
            @RequestBody DrawingTransfer2Dto.Request request);

    @PostMapping(value = "/CheckOnDrawingTransfer2.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String CheckOnDrawingTransfer2(
            @RequestHeader("Auth") String key,
            @RequestBody CheckOnDrawingTransfer2Dto.Request request);

    @PostMapping(value = "/ReceivedTransferFinAccount.nhd" , headers = "ApiNm=ReceivedTransferFinAccount")
    void ReceivedTransferFinAccount(@RequestBody ReceivedTransferFinAccountDto.Request request);

    @PostMapping(value = "/CheckOnReceivedTransfer.nhd" , headers = "ApiNm=CheckOnReceivedTransfer")
    CheckOnReceivedTransferDto.Response CheckOnReceivedTransfer(@RequestBody CheckOnReceivedTransferDto.Request request);

    @PostMapping(value = "/CheckDepositorFinAccount.nhd" , headers = "ApiNm=CheckDepositorFinAccount")
    CheckDepositorFinAccountDto.Response CheckDepositorFinAccount(@RequestBody CheckDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireDepositorFinAccount.nhd" , headers = "ApiNm=InquireDepositorFinAccount")
    InquireDepositorFinAccountDto.Response InquireDepositorFinAccount(@RequestBody InquireDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireBalance.nhd" , headers = "ApiNm=InquireBalance")
    InquireBalanceDto.Response InquireBalance(@RequestBody InquireBalanceDto.Request request);

    @PostMapping(value = "/InquireTransactionHistory.nhd" , headers = "ApiNm=InquireTransactionHistory")
    InquireTransactionHistoryDto.Response InquireTransactionHistory(@RequestBody InquireTransactionHistoryDto.Request request);

    @PostMapping(value = "/P2PNVirtualAccountNumberRequest.nhd" , headers = "ApiNm=P2PNVirtualAccountNumberRequest")
    String P2PNVirtualAccountNumberRequest();

    @PostMapping(value = "/P2PNVirtualAccountTerminate.nhd" , headers = "ApiNm=P2PNVirtualAccountTerminate")
    String P2PNVirtualAccountTerminate();
}
