package com.ssafy.account.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static feign.Feign.*;

@Configuration
public class ClientConfig {
    @Bean
    public Builder feignBuilder(Client feignClient) {
        return builder()
                .client(feignClient);
    }

}
