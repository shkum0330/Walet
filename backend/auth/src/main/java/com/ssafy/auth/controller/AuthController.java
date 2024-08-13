package com.ssafy.auth.controller;

import com.ssafy.auth.api.LoginDto;
import com.ssafy.auth.api.PinCheckDto;
import com.ssafy.auth.service.impl.AuthServiceImpl;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.external.service.NHService;
import com.ssafy.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ssafy.global.common.status.SuccessCode.GENERAL_SUCCESS;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl userService;
    private final NHService nhService;

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<TokenMapping>> login(@RequestBody LoginDto loginRequest , HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
        TokenMapping token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return new ResponseEntity<EnvelopeResponse<TokenMapping>>(new EnvelopeResponse<>(GENERAL_SUCCESS, token), HttpStatus.OK);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<EnvelopeResponse<TokenMapping>> adminLogin(@RequestBody LoginDto loginRequest , HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
        TokenMapping token = userService.adminLogin(loginRequest.getEmail(), loginRequest.getPassword());

        return new ResponseEntity<EnvelopeResponse<TokenMapping>>(new EnvelopeResponse<>(GENERAL_SUCCESS, token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<EnvelopeResponse<String>> logout(@RequestHeader(value="Authorization") String accessToken,
                                                           @RequestHeader(value="id") Long memberId) {
        accessToken = accessToken.replace("Bearer ", "");
        userService.logout(accessToken, memberId);

        return new ResponseEntity<EnvelopeResponse<String>>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/key")
    public ResponseEntity<EnvelopeResponse<String>> getKey() {
        String accessToken = nhService.getKey();
        return new ResponseEntity<EnvelopeResponse<String>>(new EnvelopeResponse<>(GENERAL_SUCCESS, accessToken), HttpStatus.OK);
    }

    @PostMapping("/pincheck")
    public ResponseEntity<EnvelopeResponse<String>> pincheck(@RequestHeader(value="Authorization") String token, @RequestBody PinCheckDto request) {
        String pinNumber = request.getPinNumber();
        userService.pinCheck(token, pinNumber);
        return new ResponseEntity<>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }
}

