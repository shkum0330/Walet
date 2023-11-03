package com.ssafy.auth.controller;

import com.ssafy.auth.api.LoginDto;
import com.ssafy.auth.service.UserServiceImpl;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.external.dto.NHDto;
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
    private final UserServiceImpl userService;
    private final NHService nhService;

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<TokenMapping>> login(@RequestBody LoginDto loginRequest , HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
        TokenMapping token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return new ResponseEntity<EnvelopeResponse<TokenMapping>>(new EnvelopeResponse<>(GENERAL_SUCCESS, token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<EnvelopeResponse<String>> logout(@RequestHeader(value="Authorization") String token) {
        token = token.replace("Bearer ", "");
        userService.userLogout(token);

        return new ResponseEntity<EnvelopeResponse<String>>(new EnvelopeResponse<>(GENERAL_SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/key")
    public ResponseEntity<EnvelopeResponse<NHDto.Response>> getKey() {
        NHDto.Response response = nhService.getKey();
        System.out.println(response);
        return new ResponseEntity<EnvelopeResponse<NHDto.Response>>(new EnvelopeResponse<>(GENERAL_SUCCESS, response), HttpStatus.OK);
    }
}

