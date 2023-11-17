package com.ssafy.service.config;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class CustomDecoder implements Decoder {
    private final Decoder decoder;

    public CustomDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.decoder = new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // 체크해서 'text/json' 컨텐츠 타입을 처리하거나 기본 디코더로 위임
        Collection<String> contentTypes = response.headers().get("Content-Type");
        String contentType = (contentTypes != null && !contentTypes.isEmpty()) ? contentTypes.iterator().next() : "";
        System.out.println(contentType);
        System.out.println(response);
        System.out.println(type);
        System.out.println(decoder);
        if (contentType.contains("text/json")) {
            // 'text/json' 처리 로직 (여기서는 단순히 기본 디코더를 사용합니다)
            return decoder.decode(response, type);
        }
        return decoder.decode(response, type); // 기본 처리
    }
}
