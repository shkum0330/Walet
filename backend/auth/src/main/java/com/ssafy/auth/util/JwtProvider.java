package com.ssafy.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Data
public class JwtProvider {

    private MemberRepository memberRepository;

    @Value("all4u")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String ROLE_CLAIM = "role";
    private static final String BEARER = "Bearer ";

    public TokenMapping createToken(Long id, String role,String name){
        return TokenMapping.builder()
                .accessToken(createAccessToken(id, role))
                .refreshToken(createRefreshToken())
                .userName(name)
                .build();
    }

    public String createAccessToken(Long id, String role){
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .withClaim(ROLE_CLAIM, role)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken(){
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public Long getExpiration(String accessToken){
        Date expiration = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken).getExpiresAt();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    public Long AccessTokenDecoder(String accessToken) {
        accessToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = JWT.decode(accessToken);
        Claim IDCLAIM = jwt.getClaim(ID_CLAIM);
        if (!IDCLAIM.isNull()) {
            return IDCLAIM.asLong();
        } else {
            throw new GlobalRuntimeException("토큰에 해당하는 회원이 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public String RoleDecoder(String accessToken) {
        accessToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = JWT.decode(accessToken);
        Claim roleClaim = jwt.getClaim(ROLE_CLAIM);
        if (!roleClaim.isNull()) {
            return roleClaim.asString();
        } else {
            throw new GlobalRuntimeException("토큰에 해당하는 회원이 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
