package com.ssafy.global.config;

import com.ssafy.global.FeignClientExceptionErrorDecoder;
import feign.Client;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
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
@EnableFeignClients(basePackages = "com.ssafy")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {
    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private int proxyPort;

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer(){
        return new Retryer.Default(1000,2000,3);
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

    @Bean
    public Client feignClient() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .build();

        return new ApacheHttpClient(httpClient);
    }

}
