package com.ssafy.auth.service;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.global.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserRepository{
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    private final TokenRedisService tokenRedisService;

    @Autowired
    public UserServiceImpl(MemberRepository memberRepository, JwtProvider jwtProvider, TokenRedisService tokenRedisService) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.tokenRedisService = tokenRedisService;
    }

    @Override
    public TokenMapping login(String email, String password) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email address: " + email));

        if (!PasswordEncoder.checkPass(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        TokenMapping tokenMapping = jwtProvider.createToken(member.getEmail());
        tokenRedisService.saveToken(email, tokenMapping.getAccessToken());
        tokenRedisService.saveToken("refresh_" + email, tokenMapping.getRefreshToken());  // 리프레시 토큰 저장

        String storedAccessToken = tokenRedisService.getToken(email);
        String storedRefreshToken = tokenRedisService.getToken("refresh_" + email);
        System.out.println("엑세스 토큰: " + storedAccessToken);
        System.out.println("리프레시 토큰: " + storedRefreshToken);


        return tokenMapping;
    }

}
