package com.ssafy.auth.controller;

import com.ssafy.auth.api.LoginDTO;
import com.ssafy.auth.service.UserServiceImpl;
import com.ssafy.auth.util.TokenMapping;
import com.ssafy.global.common.response.EnvelopeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<TokenMapping>> login(@RequestBody LoginDTO loginRequest) {
        TokenMapping token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return new ResponseEntity<EnvelopeResponse<TokenMapping>>(new EnvelopeResponse<>("ok", token), HttpStatus.OK);
    }
    
}

