package com.ssafy.account.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class FeignRetryConfiguration {
    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

}
