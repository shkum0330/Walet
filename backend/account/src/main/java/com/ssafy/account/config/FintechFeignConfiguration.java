package com.ssafy.account.config;

import com.ssafy.account.common.domain.util.TimeUtil;
import feign.Client;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.httpclient.ApacheHttpClient;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "com.ssafy.external.fintech")
@Import(FeignClientsConfiguration.class)
public class FintechFeignConfiguration {
    private final TimeUtil timeUtil;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private int proxyPort;

//    @Bean
//    Logger.Level feignLoggerLevel(){
//        return Logger.Level.FULL;
//    }

//    @Bean
//    public ErrorDecoder errorDecoder(){
//        return new FeignClientExceptionErrorDecoder();
//    }

//    @Bean
//    public Retryer retryer(){
//        return new Retryer.Default(1000,2000,3);
//    }

    @Bean
    public Client feignClient() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .build();

        return new ApacheHttpClient(httpClient);
    }

    @Bean
    public Client feignClientProxy() {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();

        return new ApacheHttpClient(httpClient);
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return template -> {
//            template.header("Tsymd", "Value2");
//            template.header("Trtm", "Value2");
//            template.header("Iscd", "Value2");
//            template.header("FintechApsno", "Value2");
//            template.header("ApiSvcCd", "Value2");
//            template.header("IsTuno", "Value2");
//            template.header("LritCd", "1");
//
//            // 다른 공통 헤더를 추가할 수 있습니다.
//        };
//    }
}
