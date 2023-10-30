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
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
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
//            토큰 보유 확인
            List<String> validateToken = jwtUtil.getHeaderToken(request);
            if (validateToken == null){
                return onError(exchange, ErrorCode.NOT_EXISTS_AUTHORIZATION);
            }
            String tokenType = validateToken.get(0).split(" ")[0];
//             Bearer 타입 검증
            if(!tokenType.equals("Bearer")){
                return onError(exchange, ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
            }
            String token = validateToken.get(0).split(" ")[1];

            if(String.valueOf(JWT.decode(token).getClaim("sub")).equals("AccessToken")){
                return onError(exchange, ErrorCode.NOT_ACCESS_TOKEN_TYPE);
            };

            System.out.println(JWT.decode(token).getClaims());
            System.out.println(JWT.decode(token).getPayload());
            System.out.println(JWT.decode(token).getHeader());

//            if (accessToken != null && jwtUtil.tokenValidation(accessToken.get(0))) {
//                request.mutate().header("Auth", "true").build();
//                request.mutate().header("Account-Value", jwtUtil.getEmailFromToken(accessToken.get(0))).build();
//                return chain.filter(exchange);
//            }

//            request.mutate().header("Auth", "false").build();
            return chain.filter(exchange);
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
