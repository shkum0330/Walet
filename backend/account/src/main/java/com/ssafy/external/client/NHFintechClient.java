package com.ssafy.external.client;

import com.ssafy.account.config.ClientConfig;
import com.ssafy.external.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://43.201.195.182/api/service" , name = "NHFintechClient" , configuration = ClientConfig.class)
public interface NHFintechClient {
    @PostMapping(value = "/OpenFinAccountARS")
    OpenFinAccountARSDto.Response OpenFinAccountARS(@RequestBody OpenFinAccountARSDto.Request request);

    @PostMapping(value = "/CheckOpenFinAccount")
    CheckOpenFinAccountDto.Response CheckOpenFinAccount(@RequestBody CheckOpenFinAccountDto.Request request);

    @PostMapping(value = "/CloseFinAccount")
    FinAccountDto.Response CloseFinAccount(@RequestBody FinAccountDto.Request request);

    @PostMapping(value = "/InquireFinAccountStatus")
    FinAccountDto.Response InquireFinAccountStatus(@RequestBody FinAccountDto.Request request);

    @PostMapping(value = "/DrawingTransfer2")
    DrawingTransfer2Dto.Response DrawingTransfer2(@RequestBody DrawingTransfer2Dto.Request request);

    @PostMapping(value = "/CheckOnDrawingTransfer2")
    CheckOnDrawingTransfer2Dto.Response CheckOnDrawingTransfer2(@RequestBody CheckOnDrawingTransfer2Dto.Request request);

    @PostMapping(value = "/ReceivedTransferFinAccount")
    ReceivedTransferFinAccountDto.Response ReceivedTransferFinAccount(@RequestBody ReceivedTransferFinAccountDto.Request request);

    @PostMapping(value = "/CheckOnReceivedTransfer")
    CheckOnReceivedTransferDto.Response CheckOnReceivedTransfer(@RequestBody CheckOnReceivedTransferDto.Request request);

    @PostMapping(value = "/CheckDepositorFinAccount")
    CheckDepositorFinAccountDto.Response CheckDepositorFinAccount(@RequestBody CheckDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireDepositorFinAccount")
    InquireDepositorFinAccountDto.Response InquireDepositorFinAccount(@RequestBody InquireDepositorFinAccountDto.Request request);

    @PostMapping(value = "/InquireBalance")
    InquireBalanceDto.Response InquireBalance(@RequestBody InquireBalanceDto.Request request);

    @PostMapping(value = "/InquireTransactionHistory")
    InquireTransactionHistoryDto.Response InquireTransactionHistory(@RequestBody InquireTransactionHistoryDto.Request request);

    @PostMapping(value = "/P2PNVirtualAccountNumberRequest")
    P2PNVirtualAccountNumberRequestDto.Response P2PNVirtualAccountNumberRequest(P2PNVirtualAccountNumberRequestDto.Request request);

    @PostMapping(value = "/P2PNVirtualAccountTerminate")
    P2PNVirtualAccountTerminateDto.Response P2PNVirtualAccountTerminate(P2PNVirtualAccountTerminateDto.Request request);

    @PostMapping(value = "/P2PNInvestmentManagementVirtualAccountList")
    P2PNInvestmentManagementVirtualAccountListDto.Response P2PNInvestmentManagementVirtualAccountList(P2PNInvestmentManagementVirtualAccountListDto.Request request);
}
