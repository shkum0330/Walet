package com.ssafy.member.controller;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.global.common.response.EnvelopeResponse;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.api.VerificationDto;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private RedisService redisService;

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponse<MemberEntity>> signup(@RequestBody MemberDto.MemberRequest request) {
        MemberEntity member = memberService.signup(request.getName(), request.getEmail(),
                request.getPassword(), request.getPhoneNumber(),
                request.getBirth(), request.getPinNumber(), request.getFingerPrint());
        return new ResponseEntity<>(new EnvelopeResponse<>(201, "데이터 생성 성공", member), HttpStatus.OK);
    }

    @DeleteMapping("/signout")
    public ResponseEntity<EnvelopeResponse<String>> unregister(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        memberService.Signout(id);
        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", ""), HttpStatus.BAD_REQUEST);
    }

    @GetMapping ("/user")
    public ResponseEntity<EnvelopeResponse<MemberDto.UserResponse>> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        MemberDto.UserResponse user = memberService.find(id);

        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<EnvelopeResponse<List<MemberDto.UsersResponse>>> getAllUsers(@RequestHeader("Authorization") String accessToken) {
        List<MemberDto.UsersResponse> allUsers = memberService.getAllUsers(accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", allUsers), HttpStatus.OK);
    }


    @PostMapping("/checkemail")
    public ResponseEntity<EnvelopeResponse<Boolean>> checkEmailExists(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        boolean emailExists = memberService.checkEmailExists(email);
        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", emailExists), HttpStatus.OK);
    }

    @PostMapping("/sendcode")
    public ResponseEntity<EnvelopeResponse<String>> sendVerificationCode(@RequestBody VerificationDto.PhoneRequest request) {
        String verificationCode = memberService.createVerificationCode();
        redisService.saveTokenWithExpiration(request.getPhoneNumber(), verificationCode, 300, TimeUnit.SECONDS);
        smsUtil.sendOne(request.getPhoneNumber(), verificationCode);

        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", ""), HttpStatus.OK);
    }

    @PostMapping("/checkcode")
    public ResponseEntity<EnvelopeResponse<String>> verifyCode(@RequestBody VerificationDto.CodeRequest request) {
        String savedCode = redisService.getToken(request.getPhoneNumber());
        memberService.verificationCode(request.getCode(), savedCode);
        return new ResponseEntity<>(new EnvelopeResponse<>(200, "데이터 처리 성공", ""), HttpStatus.OK);
    }

    @GetMapping("/name/{id}")
    public ResponseEntity<String> getUserName(@PathVariable Long id) {
        String name = memberService.findNameById(id);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

}