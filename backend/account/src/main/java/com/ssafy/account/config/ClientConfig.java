package com.ssafy.account.config;

import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public Feign.Builder feignBuilder(Client feignClient) {
        return Feign.builder()
                .client(feignClient);
    }


}
