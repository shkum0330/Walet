package com.ssafy.service.api;

import com.ssafy.service.api.dto.FinAccountServiceDto;
import com.ssafy.service.external.service.NHFintechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final NHFintechService nhFintechService;
    @PostMapping("/OpenFinAccountARS")
    public ResponseEntity<String> OpenFinAccountARS(@RequestBody FinAccountServiceDto.Request request){
        return ResponseEntity.ok(nhFintechService.OpenFinAccountARS(request));
    }
}
