package com.ssafy.service.api;

import com.ssafy.service.external.dto.OpenFinAccountARSDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Void> Test(){
        String name = "test";
        log.error("error log={}",name);
        log.warn("warn log={}",name);
        log.info("info log={}",name);
        log.debug("debug log={}",name);
        log.trace("trace log={}",name);
        return ResponseEntity.ok(null);
    }
}
