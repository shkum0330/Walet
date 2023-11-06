package com.ssafy.account.config;

import com.ssafy.account.common.domain.util.TimeUtil;
import com.ssafy.external.client.OauthClient;
import com.ssafy.external.service.OauthService;
import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ClientProxyConfig {
    private final TimeUtil timeUtil;
    private final OauthService oauthService;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("Auth", "Basic " + oauthService.getOauthKey());
            template.header("Tsymd", timeUtil.YMD(LocalDateTime.now()));
            template.header("Trtm", timeUtil.HMS(LocalDateTime.now()));
            template.header("Iscd", "000449");
            template.header("FintechApsno", "001");
            template.header("ApiSvcCd", "01E_024_00");
            template.header("IsTuno", timeUtil.YMDHMS(LocalDateTime.now()));
            template.header("Content-Type", "application/octet-stream");
//            template.header("LritCd", "1");
        };
    }

    @Bean
    public Feign.Builder feignBuilder(Client feignClientProxy  , RequestInterceptor requestInterceptor) {
        return Feign.builder()
                .requestInterceptor(requestInterceptor)
                .client(feignClientProxy);
    }
}
