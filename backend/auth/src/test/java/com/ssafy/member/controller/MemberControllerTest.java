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

import static com.ssafy.docs.ApiDocumentUtils.getDocumentRequest;
import static com.ssafy.docs.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        MemberDto.MemberRequest memberRequest=new MemberDto.MemberRequest("김민지","minji@naver.com"
                ,"1234","010-1234-1234","2004-05-07",null,"000000");
        Member expectedMember= MemberFixture.DEFAULT.getMember();

        //when
        given(memberService.signUp(any(MemberDto.MemberRequest.class))).willReturn(expectedMember);

        // then
        ResultActions perform=mockMvc.perform(
                post("/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(memberRequest)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.data.id").value(1L))
                        .andExpect(jsonPath("$.data.name").value("김민지"))
                        .andExpect(jsonPath("$.data.email").value("minji@naver.com"))
                        .andExpect(jsonPath("$.data.password").value("1234"))
                        .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-1234"))
                        .andExpect(jsonPath("$.data.birth").value("2004-05-07"))
                        .andExpect(jsonPath("$.data.pinNumber").value("000000"));

    }

}