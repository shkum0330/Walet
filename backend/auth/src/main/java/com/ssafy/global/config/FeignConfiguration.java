package com.ssafy.global.config;

import com.ssafy.global.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableFeignClients(basePackages = "com.ssafy")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
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
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
