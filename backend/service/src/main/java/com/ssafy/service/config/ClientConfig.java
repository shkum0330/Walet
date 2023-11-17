package com.ssafy.service.config;

import feign.Client;
import feign.Feign;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ClientConfig {
    @Bean
    public Feign.Builder feignBuilder(Client feignClient) {
        return Feign.builder()
                .client(feignClient);
    }
}
