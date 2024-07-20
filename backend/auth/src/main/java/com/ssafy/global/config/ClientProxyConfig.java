package com.ssafy.global.config;

import feign.Client;
import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {
    @Bean
    public Feign.Builder feignBuilder(Client feignClientProxy) {
        return Feign.builder()
                .client(feignClientProxy);
    }
}
