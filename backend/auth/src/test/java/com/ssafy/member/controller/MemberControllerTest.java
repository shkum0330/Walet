package com.ssafy.member.controller;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.docs.RestDocsTest;
import com.ssafy.fixture.MemberFixture;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest extends RestDocsTest {

    @MockBean
    MemberService memberService;
    @MockBean
    JwtProvider jwtProvider;
    @MockBean
    SmsUtil smsUtil;
    @MockBean
    RedisService redisService;

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberDto.MemberRequest memberRequest=new MemberDto.MemberRequest("강해린","haerin@naver.com"
                ,"1234","010-1234-1234","2006-05-15",null,"000000");
        Member expectedMember= MemberFixture.DEFAULT.getMember();

        //when
        memberService.signUp(memberRequest);

        //then
        ResultActions perform=mockMvc.perform(
                post("/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(memberRequest)));
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("강해린"));

    }

}