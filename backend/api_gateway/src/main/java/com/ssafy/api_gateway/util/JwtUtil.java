package com.ssafy.api_gateway.util;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {
    public void tokenValidation(){
        try {

        } catch (ExpiredJwtException e){

        }
    }

    public String getHeaderToken(ServerHttpRequest request , String msg){
        return request.getHeaders().get("Authorization").get(1);
    }

//    public Mono<Void> onError(ServerWebExchange exchange , String err , HttpStatus httpStatus){
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        log.error(err);
//        return response.setComplete();
//    }
}
