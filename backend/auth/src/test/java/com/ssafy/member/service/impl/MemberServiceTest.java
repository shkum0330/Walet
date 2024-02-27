package com.ssafy.member.service.impl;

import com.ssafy.auth.api.LoginDto;
import com.ssafy.auth.service.AuthService;
import com.ssafy.auth.service.impl.AuthServiceImpl;
import com.ssafy.auth.util.JwtProvider;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.Member;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    SmsUtil smsUtil;
    @Autowired
    RedisService redisService;

    @BeforeEach
    public void setInitMember(){
        MemberDto.MemberRequest memberRequest=new MemberDto.MemberRequest("강해린","haerin@naver.com"
                ,"1234","010-1234-1234","2006-05-15",null,"000000");
        memberService.signUp(memberRequest);
    }

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        MemberDto.MemberRequest memberRequest=new MemberDto.MemberRequest("김민지","minji@naver.com"
                ,"1234","010-1234-1234","2004-05-07",null,"000000");
        //when
        Member member=memberService.signUp(memberRequest);

        //then
        assertThat(member.getName()).isEqualTo("김민지");
        assertThat(PasswordEncoder.checkPass("1234",member.getPassword())).isTrue();
    }

    @Test
    @Rollback(false)
    public void 로그인_성공() throws Exception {
        //given

        //when
        TokenMapping token = authService.login("haerin@naver.com","1234");

        //then
        assertThat(token.getUserName()).isEqualTo("강해린");
        log.info("username: {},  {} {}",token.getUserName(),token.getAccessToken(),token.getRefreshToken());
        log.info("accessToken: {}",token.getAccessToken());
        log.info("refreshToken: {}",token.getRefreshToken());

    }

    @Test
    public void 로그아웃_성공() throws Exception {
        //given
        TokenMapping token = authService.login("haerin@naver.com","1234");
        //when
        authService.logout(token.getAccessToken());
        //then
        assertThat(redisService.isBlackListed(token.getAccessToken())).isTrue();
    }

    @Test
    public void 로그아웃_실패() throws Exception {
        //given
        TokenMapping token = authService.login("haerin@naver.com","1234");
        //when
        authService.logout(token.getAccessToken());
        authService.logout(token.getAccessToken()); // 이미 블랙리스트로 들어간 토큰
        //then
        assertThat(redisService.isBlackListed(token.getAccessToken())).isTrue();
    }
}