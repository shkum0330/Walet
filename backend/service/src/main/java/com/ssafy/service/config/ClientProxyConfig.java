package com.ssafy.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientProxyConfig {
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Bean
    public Feign.Builder feignBuilder(Client feignClientProxy , Encoder encoder , Decoder decoder) {
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .client(feignClientProxy);
    }

    @Bean
    @Primary
    public Encoder feignEncoder() {
        return new ByteArrayEncoder(new ObjectMapper());
    }

    @Bean
    @Primary
    public Decoder feignDecoder() {
        return new CustomDecoder(messageConverters);
    }
}
