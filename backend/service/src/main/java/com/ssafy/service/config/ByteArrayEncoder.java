package com.ssafy.service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class ByteArrayEncoder implements Encoder {
    private final ObjectMapper objectMapper;
    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        System.out.println("test" + bodyType);
        if (bodyType.equals(byte[].class)) {
            // 바이트 배열로 이미 인코딩된 경우 그대로 사용
            template.body((byte[]) object, null); // null은 charset을 지정하지 않음을 의미
        } else {
            // 객체를 JSON으로 변환하고, 바이트 배열로 인코딩
            try {
                template.body(objectMapper.writeValueAsBytes(object) , StandardCharsets.UTF_8);
            } catch (JsonProcessingException e) {
                throw new EncodeException("Error converting request body to JSON", e);
            }
        }
    }
}
