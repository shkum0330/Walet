package com.ssafy.member.controller;

import com.ssafy.member.api.MemberDTO;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDTO.noticeResponse> signup(@RequestBody MemberDTO.request request) {
        MemberEntity member = memberService.signup(request.getName(), request.getEmail(),
                request.getPassword(), request.getPhoneNumber(),
                request.getBirth());
        MemberDTO.noticeResponse response = new MemberDTO.noticeResponse();
        response.setMessage("ok");
        response.setData(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}