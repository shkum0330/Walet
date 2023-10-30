package com.ssafy.api_gateway.filter;

import com.ssafy.api_gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtUtil jwtUtil;
    @Autowired
    public AuthFilter(JwtUtil jwtUtil) {
        super(AuthFilter.Config.class);
        this.jwtUtil = jwtUtil;
    }

    public static class Config{
        //config
    }

    @Override
    public GatewayFilter apply(AuthFilter.Config config) {
        return (exchange, chain) -> {
            //ServerHttpRequest
            ServerHttpRequest request = exchange.getRequest();
            String accessToken = jwtUtil.getHeaderToken(request, "Access");
//            List<String> refreshToken = jwtUtil.getHeaderToken(request, "Refresh");


//            if (accessToken != null && jwtUtil.tokenValidation(accessToken.get(0))) {
//                request.mutate().header("Auth", "true").build();
//                request.mutate().header("Account-Value", jwtUtil.getEmailFromToken(accessToken.get(0))).build();
//                return chain.filter(exchange);
//            }

//            request.mutate().header("Auth", "false").build();
            return chain.filter(exchange);
        };
    }
}
