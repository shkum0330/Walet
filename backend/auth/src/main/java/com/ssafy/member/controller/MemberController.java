package com.ssafy.member.controller;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.redis.RedisService;
import com.ssafy.global.common.response.EnvelopeResponse;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.api.VerificationDto;
import com.ssafy.member.db.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ssafy.global.common.status.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final SmsUtil smsUtil;
    private final RedisService redisService;

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponse<Member>> signup(@RequestBody MemberDto.MemberRequest memberRequest) {
        Member member = memberService.signUp(memberRequest);
        return new ResponseEntity<>(new EnvelopeResponse<>(CREATE_SUCCESS, member), HttpStatus.CREATED);
    }

    @DeleteMapping("/signout")
    public ResponseEntity<EnvelopeResponse<String>> signOut(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        memberService.SignOut(id);
        return new ResponseEntity<>(new EnvelopeResponse<>(DELETE_SUCCESS, ""), HttpStatus.NO_CONTENT);
    }

    @GetMapping ("/user")
    public ResponseEntity<EnvelopeResponse<MemberDto.UserResponse>> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        Long id = jwtProvider.AccessTokenDecoder(accessToken);
        MemberDto.UserResponse user = memberService.findById(id);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<EnvelopeResponse<List<MemberDto.UsersResponse>>> getAllUsers(@RequestHeader("Authorization") String accessToken) {
        List<MemberDto.UsersResponse> allUsers = memberService.getAllUsers(accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, allUsers), HttpStatus.OK);
    }

    @GetMapping("/user/search")
    public ResponseEntity<EnvelopeResponse<List<MemberDto.UsersResponse>>> searchUser(@RequestParam String keyword, @RequestHeader("Authorization") String accessToken) {
        List<MemberDto.UsersResponse> users = memberService.searchUser(keyword, accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, users), HttpStatus.OK);
    }


    @PostMapping("/checkemail")
    public ResponseEntity<EnvelopeResponse<Boolean>> checkEmailExists(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        boolean emailExists = memberService.checkEmailExists(email);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, emailExists), HttpStatus.OK);
    }

    @PostMapping("/sendcode")
    public ResponseEntity<EnvelopeResponse<String>> sendVerificationCode(@RequestBody VerificationDto.PhoneRequest request) {
        String verificationCode = memberService.createVerificationCode();
        redisService.saveTokenWithExpiration(request.getPhoneNumber(), verificationCode, 300, TimeUnit.SECONDS);
        smsUtil.sendOne(request.getPhoneNumber(), verificationCode);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @PostMapping("/checkcode")
    public ResponseEntity<EnvelopeResponse<String>> verifyCode(@RequestBody VerificationDto.CodeRequest request) {
        String savedCode = redisService.getToken(request.getPhoneNumber());
        memberService.verificationCode(request.getCode(), savedCode);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/name/{memberId}")
    public ResponseEntity<EnvelopeResponse<MemberDto.NameResponse>> getUserName(@PathVariable Long memberId) {
        MemberDto.NameResponse name = memberService.findNameById(memberId);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, name), HttpStatus.OK);
    }

    @GetMapping("/phone-number/{memberId}")
    public ResponseEntity<EnvelopeResponse<MemberDto.PhoneResponse>> getUserPhone(@PathVariable Long memberId) {
        MemberDto.PhoneResponse phone = memberService.findPhoneById(memberId);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, phone), HttpStatus.OK);
    }

    @GetMapping("/dashboard/count")
    public ResponseEntity<EnvelopeResponse<MemberDto.CountResponse>> countUsersRegisteredWithinLastWeek(@RequestHeader("Authorization") String accessToken) {
        MemberDto.CountResponse userCount = memberService.countDashBoardData(7, accessToken);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, userCount), HttpStatus.OK);
    }

    @PostMapping("/admin/user/{memberId}")
    public ResponseEntity<EnvelopeResponse<String>> revise(@RequestHeader("Authorization") String accessToken, @PathVariable Long memberId) {
        memberService.reviseMember(memberId);

        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);

    }
}