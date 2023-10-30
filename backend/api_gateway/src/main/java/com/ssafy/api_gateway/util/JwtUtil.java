package com.ssafy.api_gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtil {


    public List<String>  getHeaderToken(ServerHttpRequest request){
        return request.getHeaders().get("Authorization");
    }
}
