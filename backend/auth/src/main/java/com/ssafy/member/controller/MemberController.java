package com.ssafy.member.controller;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.global.common.response.EnvelopeResponse;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.api.VerificationDto;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.service.impl.MemberServiceImpl;
import com.ssafy.member.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ssafy.global.common.status.SuccessCode.*;

@RestController
public class MemberController {
    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private RedisService redisService;

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponse<MemberEntity>> signup(@RequestBody MemberDto.MemberRequest request) {
        MemberEntity member = memberServiceImpl.signUp(request.getName(), request.getEmail(),
                request.getPassword(), request.getPhoneNumber(),
                request.getBirth(), request.getPinNumber(), request.getFingerPrint());
        return new ResponseEntity<>(new EnvelopeResponse<>(CREATE_SUCCESS, member), HttpStatus.CREATED);
    }

    @DeleteMapping("/signout")
    public ResponseEntity<EnvelopeResponse<String>> signOut(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        memberServiceImpl.SignOut(id);
        return new ResponseEntity<>(new EnvelopeResponse<>(DELETE_SUCCESS, ""), HttpStatus.NO_CONTENT);
    }

    @GetMapping ("/user")
    public ResponseEntity<EnvelopeResponse<MemberDto.UserResponse>> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        MemberDto.UserResponse user = memberServiceImpl.findById(id);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<EnvelopeResponse<List<MemberDto.UsersResponse>>> getAllUsers(@RequestHeader("Authorization") String accessToken) {
        List<MemberDto.UsersResponse> allUsers = memberServiceImpl.getAllUsers(accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, allUsers), HttpStatus.OK);
    }

    @GetMapping("/user/search")
    public ResponseEntity<EnvelopeResponse<List<MemberDto.UsersResponse>>> searchUser(@RequestParam String keyword, @RequestHeader("Authorization") String accessToken) {
        List<MemberDto.UsersResponse> users = memberServiceImpl.searchUser(keyword, accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, users), HttpStatus.OK);
    }


    @PostMapping("/checkemail")
    public ResponseEntity<EnvelopeResponse<Boolean>> checkEmailExists(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        boolean emailExists = memberServiceImpl.checkEmailExists(email);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, emailExists), HttpStatus.OK);
    }

    @PostMapping("/sendcode")
    public ResponseEntity<EnvelopeResponse<String>> sendVerificationCode(@RequestBody VerificationDto.PhoneRequest request) {
        String verificationCode = memberServiceImpl.createVerificationCode();
        redisService.saveTokenWithExpiration(request.getPhoneNumber(), verificationCode, 300, TimeUnit.SECONDS);
        smsUtil.sendOne(request.getPhoneNumber(), verificationCode);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @PostMapping("/checkcode")
    public ResponseEntity<EnvelopeResponse<String>> verifyCode(@RequestBody VerificationDto.CodeRequest request) {
        String savedCode = redisService.getToken(request.getPhoneNumber());
        memberServiceImpl.verificationCode(request.getCode(), savedCode);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/name/{memberId}")
    public ResponseEntity<EnvelopeResponse<MemberDto.NameResponse>> getUserName(@PathVariable Long memberId) {
        MemberDto.NameResponse name = memberServiceImpl.findNameById(memberId);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, name), HttpStatus.OK);
    }

    @GetMapping("/phone-number/{memberId}")
    public ResponseEntity<EnvelopeResponse<MemberDto.PhoneResponse>> getUserPhone(@PathVariable Long memberId) {
        MemberDto.PhoneResponse phone = memberServiceImpl.findPhoneById(memberId);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, phone), HttpStatus.OK);
    }

    @GetMapping("/dashboard/count")
    public ResponseEntity<EnvelopeResponse<MemberDto.CountResponse>> countUsersRegisteredWithinLastWeek(@RequestHeader("Authorization") String accessToken) {
        MemberDto.CountResponse userCount = memberServiceImpl.countDashBoardData(7, accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, userCount), HttpStatus.OK);
    }

    @PostMapping("/admin/user/{memberId}")
    public ResponseEntity<EnvelopeResponse<String>> revise(@RequestHeader("Authorization") String accessToken, @PathVariable Long memberId) {
        memberServiceImpl.reviseMember(memberId);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);

    }
}