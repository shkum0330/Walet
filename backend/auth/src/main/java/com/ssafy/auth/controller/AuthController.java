package com.ssafy.auth.controller;

import com.ssafy.auth.api.LoginDto;
import com.ssafy.auth.service.UserServiceImpl;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<TokenMapping>> login(@RequestBody LoginDto loginRequest) {
        TokenMapping token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return new ResponseEntity<EnvelopeResponse<TokenMapping>>(new EnvelopeResponse<>("ok", token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<EnvelopeResponse<String>> logout(@RequestHeader(value="Authorization") String token) {
        token = token.replace("Bearer ", "");
        userService.userLogout(token);

        return new ResponseEntity<EnvelopeResponse<String>>(new EnvelopeResponse<>("ok", ""), HttpStatus.OK);
    }
}

