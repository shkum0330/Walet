package com.ssafy.external.client;

import com.ssafy.external.dto.OpenFinAccountARSDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@WebMvcTest
class NHFintechClientTest {
    private final NHFintechClient client;

    public NHFintechClientTest(NHFintechClient client) {
        this.client = client;
    }
    
    @Test
    public void 핀어카운트발급() throws Exception {
        //given
//        OpenFinAccountARSDto dto=new OpenFinAccountARSDto();
//        OpenFinAccountARSDto.Request=new OpenFinAccountARSDto()
        //when

        //then
//        log.info("{}",NHFintechClient.OpenFinAccountARS());
    }

}