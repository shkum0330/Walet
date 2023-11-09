package com.ssafy.account.api;

import com.ssafy.account.common.api.Response;
import com.ssafy.external.client.OauthClient;
import com.ssafy.external.dto.InquireDepositorFinAccountDto;
import com.ssafy.external.dto.OauthDto;
import com.ssafy.external.service.NHFintechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final NHFintechService nhFintechService;
    private final OauthClient oauthClient;

    @GetMapping("/test")
    public Response getGeneralAccountList() {
        InquireDepositorFinAccountDto.Request request = InquireDepositorFinAccountDto.Request.builder()
                .FinAcno("00820111419481425091415098899")
                .build();
//        nhFintechService.InquireDepositorFinAccount(request);
//        System.out.println(nhFintechService.OpenFinAccountARS();
        return Response.success(GENERAL_SUCCESS, nhFintechService.InquireDepositorFinAccount(request));
    }

    @GetMapping("/auth-test")
    public void oauthTest(){
        OauthDto.Response name=oauthClient.getUserName(1L);
        log.info("{}",name.getData().getName());
        OauthDto.Response pn=oauthClient.getUserPhoneNumber(1L);
        log.info("{}",name);
    }


}
