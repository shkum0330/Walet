package com.ssafy.auth.service.impl;

import com.ssafy.auth.service.AuthService;
import com.ssafy.auth.util.JwtProvider;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.member.db.Member;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.member.db.Role;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import static com.ssafy.global.common.status.FailCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public TokenMapping login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalRuntimeException(UNSIGNED_USER));

        if (!PasswordEncoder.checkPass(password, member.getPassword())) {
            throw new GlobalRuntimeException(DIFFERENT_PASSWORD);
        }

        if (member.isDeleted()) {
            throw new GlobalRuntimeException(DELETED_USER);
        }

        return getTokenMapping(member);
    }

    @Override
    public TokenMapping adminLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalRuntimeException(UNSIGNED_USER));

        if (!PasswordEncoder.checkPass(password, member.getPassword())) {
           throw new GlobalRuntimeException(DIFFERENT_PASSWORD);
        }

        if (member.isDeleted()) {
            throw new GlobalRuntimeException(DELETED_USER);
        }


        if (member.getRole().equals(Role.USER)){
            throw new GlobalRuntimeException(STAFF_ONLY);
        }

        return getTokenMapping(member);
    }

    @NotNull
    private TokenMapping getTokenMapping(Member member) {
        String role = member.getRole().name();
        TokenMapping tokenMapping = jwtProvider.createToken(member.getId(), role, member.getName());
        redisService.saveToken(member.getId().toString(), tokenMapping.getAccessToken());
        redisService.saveToken("refresh_" + member.getId().toString(), tokenMapping.getRefreshToken());
        return tokenMapping;
    }

    @Override
    public void logout(String accessToken,Long id){
        if (redisService.isBlackListed(accessToken)) {
            throw new GlobalRuntimeException(BAD_TOKEN);
        }
        Long expiration = jwtProvider.getExpiration(accessToken);
        if(redisService.getToken("refresh_"+id.toString()) != null){ // 리프레시 토큰 삭제
            redisService.deleteToken("refresh_"+id.toString());
        }
        redisService.setBlackList(accessToken, "logout", expiration); // 엑세스 토큰 블랙리스트
    }

    public void pinCheck(String accessToken, String pinNumber){
        Long userId = jwtProvider.AccessTokenDecoder(accessToken);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GlobalRuntimeException(UNSIGNED_USER));

        if (!member.getPinNumber().equals(pinNumber)) {
            throw new GlobalRuntimeException(DIFFERENT_PIN);
        }
    }
}