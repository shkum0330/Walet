package com.ssafy.auth.service;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.global.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserRepository{
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Autowired
    public UserServiceImpl(MemberRepository memberRepository, JwtProvider jwtProvider, RedisService redisService) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
    }

    @Override
    public TokenMapping login(String email, String password) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다. " + email));

        if (!PasswordEncoder.checkPass(password, member.getPassword())) {
            throw new GlobalRuntimeException("비밀번호가 틀립니다.", HttpStatus.BAD_REQUEST);
        }

        if (member.isDeleted()) {
            throw new GlobalRuntimeException("회원 탈퇴된 계정입니다.", HttpStatus.BAD_REQUEST);
        }

        TokenMapping tokenMapping = jwtProvider.createToken(member.getRandomMemberId());
        redisService.saveToken(member.getRandomMemberId(), tokenMapping.getAccessToken());
        redisService.saveToken("refresh_" + email, tokenMapping.getRefreshToken());

        return tokenMapping;
    }

    public void userLogout(String accessToken){
        if (redisService.isBlackListed(accessToken)) {
            throw new GlobalRuntimeException("사용할 수 없는 토큰입니다.",HttpStatus.BAD_REQUEST);
        }
        Long expiration = jwtProvider.getExpiration(accessToken);
        redisService.setBlackList(accessToken, accessToken, expiration);
    }
}