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

    @PostMapping(value = "/ReceivedTransferFinAccount.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String ReceivedTransferFinAccount(
            @RequestHeader("Auth") String key,
            @RequestBody ReceivedTransferFinAccountDto.Request request);

    @PostMapping(value = "/CheckOnReceivedTransfer.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String CheckOnReceivedTransfer(
            @RequestHeader("Auth") String key,
            @RequestBody CheckOnReceivedTransferDto.Request request);

    @PostMapping(value = "/CheckDepositorFinAccount.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String CheckDepositorFinAccount(
            @RequestHeader("Auth") String key,
            @RequestBody CheckDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireDepositorFinAccount.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String InquireDepositorFinAccount(
            @RequestHeader("Auth") String key,
            @RequestBody InquireDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireBalance.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    InquireBalanceDto.Response InquireBalance(
            @RequestHeader("Auth") String key,
            @RequestBody InquireBalanceDto.Request request);

    @PostMapping(value = "/InquireTransactionHistory.nhd" , consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    String InquireTransactionHistory(
            @RequestHeader("Auth") String key,
            @RequestBody InquireTransactionHistoryDto.Request request);

    @PostMapping(value = "/P2PNVirtualAccountNumberRequest.nhd" , headers = "ApiNm=P2PNVirtualAccountNumberRequest")
    String P2PNVirtualAccountNumberRequest();

    @PostMapping(value = "/P2PNVirtualAccountTerminate.nhd" , headers = "ApiNm=P2PNVirtualAccountTerminate")
    String P2PNVirtualAccountTerminate();
}
