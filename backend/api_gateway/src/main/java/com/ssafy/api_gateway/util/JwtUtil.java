package com.ssafy.api_gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.api_gateway.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtil {
    public ErrorCode validateToken(ServerHttpRequest request){
        //토큰 있는지 검사
        List<String> header = request.getHeaders().get("Authorization");
        if (header == null){
            return ErrorCode.NOT_EXISTS_AUTHORIZATION;
        }

        if(header.size() != 1){
            return ErrorCode.NOT_VALID_TOKEN;
        }
//      Bearer 타입 검사
        String[] token = header.get(0).split(" ");
        if(!token[0].equals("Bearer")){
            return ErrorCode.NOT_VALID_BEARER_GRANT_TYPE;
        }
        
//        AccessToken 검사
        DecodedJWT decodedJWT = JWT.decode(token[1]);
        if(!String.valueOf(decodedJWT.getClaim("sub")).equals("\"AccessToken\"")){
            return ErrorCode.NOT_ACCESS_TOKEN_TYPE;
        };

//       만료시간 검사
        Date now = new Date();
//        System.out.println(now.getTime());
//        System.out.println(now.getTime() + 3600000L);
//        System.out.println(decodedJWT.getClaims());
//        System.out.println();
//        if(!String.valueOf(decodedJWT.getClaim("exp")).equals("1")){
//            return ErrorCode.TOKEN_EXPIRED;
//        };
        if(now.getTime() > Long.parseLong(String.valueOf(decodedJWT.getClaim("exp"))) * 1000){
            return ErrorCode.TOKEN_EXPIRED;
        }
        return null;
    }

    public String getUserType(ServerHttpRequest request) {
        return String.valueOf(JWT.decode(
                request.getHeaders().get("Authorization").get(0).split(" ")[1]).getClaim("role"));
    }

    public String getMemberId(ServerHttpRequest request){
        return String.valueOf(JWT.decode(
                request.getHeaders().get("Authorization").get(0).split(" ")[1]).getClaim("memberId"));
    }

    public String getMemberName(ServerHttpRequest request){
        return String.valueOf(JWT.decode(
                request.getHeaders().get("Authorization").get(0).split(" ")[1]).getClaim("name"));
    }
}
