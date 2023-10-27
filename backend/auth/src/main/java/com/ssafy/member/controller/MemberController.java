package com.ssafy.member.controller;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.global.common.response.EnvelopeResponse;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.api.UserDto;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponse<MemberEntity>> signup(@RequestBody MemberDto.Request request) {
        MemberEntity member = memberService.signup(request.getName(), request.getEmail(),
                request.getPassword(), request.getPhoneNumber(),
                request.getBirth(), request.getPinNumber(), request.getFingerPrint());

        return new ResponseEntity<>(new EnvelopeResponse<>(201, "데이터 생성 성공", member), HttpStatus.OK);
    }

    @DeleteMapping("/unregister")
    public ResponseEntity<EnvelopeResponse<String>> unregister(@RequestHeader("Authorization") String accessToken) {
        String randomMemberId = jwtProvider.AccessTokenDecoder(accessToken);
        memberService.Unregister(randomMemberId);

        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user")
    public ResponseEntity<EnvelopeResponse<UserDto.Response>> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        String randomMemberId = jwtProvider.AccessTokenDecoder(accessToken);
        UserDto.Response user = memberService.find(randomMemberId);
        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", user), HttpStatus.OK);
    }
}