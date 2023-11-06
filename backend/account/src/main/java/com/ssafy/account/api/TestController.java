package com.ssafy.account.api;

import com.ssafy.account.common.api.Response;
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
    @GetMapping("/test")
    public Response getGeneralAccountList() {
        System.out.println(nhFintechService.OpenFinAccountARS("모새건" , "19851221" , "01045644545","301-0361-0495-51","N"));
        return Response.success(GENERAL_SUCCESS, null);
    }
}
