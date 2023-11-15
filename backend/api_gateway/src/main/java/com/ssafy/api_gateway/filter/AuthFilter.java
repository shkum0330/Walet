package com.ssafy.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ssafy.api_gateway.error.ErrorCode;
import com.ssafy.api_gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthFilter(JwtUtil jwtUtil , ObjectMapper objectMapper) {
        super(AuthFilter.Config.class);
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    public static class Config{
        //config
    }

    @Override
    public GatewayFilter apply(AuthFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
//            토큰인증
            ErrorCode errorCode =  jwtUtil.validateToken(request);
            if(errorCode != null){
                return onError(exchange, errorCode);
            }

            String userType = jwtUtil.getUserType(request);
            //USER 가 아니면 거부
            if(!userType.equals("\"USER\"") && !userType.equals("\"ADMIN\"")){
                return onError(exchange, ErrorCode.INVALID_MEMBER_TYPE);
            }

//          헤더에 id name 추가
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header("id",jwtUtil.getMemberId(request))
                    .header("name" , jwtUtil.getMemberName(request)).build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }
    private Mono<Void> onError(ServerWebExchange exchange, ErrorCode errorCode) {
        // HTTP 상태 코드 설정
        exchange.getResponse().setStatusCode(errorCode.getHttpStatus());
        // 응답 헤더 설정
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 에러 정보를 Map으로 변환
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("code", errorCode.getCode());
        errorAttributes.put("message", errorCode.getMessage());

        // Map을 JSON 문자열로 변환
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorAttributes);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        } catch (JsonProcessingException e) {
            log.warn("Error writing response", e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }
}
