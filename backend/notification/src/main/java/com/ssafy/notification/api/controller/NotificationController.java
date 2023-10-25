package com.ssafy.notification.api.controller;


import com.ssafy.notification.api.mapper.FcmTokenMapper;
import com.ssafy.notification.api.request.FcmTokenSaveRequest;
import com.ssafy.notification.api.response.Response;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService fcmTokenService;

    @PostMapping("/")
    ResponseEntity<Response> saveMemberFcmToken(@RequestBody FcmTokenSaveRequest fcmTokenSaveRequest){
        fcmTokenService.save(FcmTokenMapper.INSTANCE.requestToEntity(fcmTokenSaveRequest));
        Response response = Response
                .builder()
                .message("OK")
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
