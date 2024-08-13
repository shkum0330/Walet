package com.ssafy.account.docs;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class RestDocsConfiguration {
    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer ->
                configurer
                        .operationPreprocessors() // MockMvc의 각 작업에 대한 사전 처리기
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint());
    }
}
