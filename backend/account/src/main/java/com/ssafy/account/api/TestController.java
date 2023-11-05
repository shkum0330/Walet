package com.ssafy.account.api;

import com.ssafy.account.common.api.Response;
import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.external.service.NHFintechService;
import com.ssafy.external.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.ssafy.account.common.api.status.SuccessCode.GENERAL_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final NHFintechService nhFintechService;
    private final TimeUtil timeUtil;

    @GetMapping("/test")
    public Response getGeneralAccountList() {
        System.out.println(LocalDateTime.now());
        System.out.println(timeUtil.YMD(LocalDateTime.now()));
//        System.out.println(oauthService.getOauthKey());
        System.out.println(nhFintechService.OpenFinAccountARS("모새건" , "19851221" , "01045644545","301-0361-0495-51","N"));
        return Response.success(GENERAL_SUCCESS, null);
    }
}
