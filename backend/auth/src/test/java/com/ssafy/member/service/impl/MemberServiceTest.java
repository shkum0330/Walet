package com.ssafy.member.service.impl;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.global.config.ClientConfig;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.FeignClient;
import com.ssafy.member.db.Member;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @MockBean
    JwtProvider jwtProvider;
    @MockBean
    SmsUtil smsUtil;
    @MockBean
    RedisService redisService;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        MemberDto.MemberRequest memberRequest=new MemberDto.MemberRequest("김민지","minji@naver.com"
                ,"1234","010-1234-1234","2004-05-07",null,"000000");
        //when
        Member member=memberService.signUp(memberRequest);

        //then
        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(PasswordEncoder.checkPass("1234",member.getPassword())).isTrue();
    }
}