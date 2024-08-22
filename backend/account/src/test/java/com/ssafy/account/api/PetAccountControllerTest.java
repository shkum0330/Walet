package com.ssafy.account.api;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.docs.RestApiTest;
import com.ssafy.fixture.AccountFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ssafy.account.docs.ApiDocumentUtils.getDocumentRequest;
import static com.ssafy.account.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(AccountController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PetAccountControllerTest extends RestApiTest {
    @MockBean
    AccountService accountService;

    @Test
    public void 일반계좌_생성() throws Exception {
        //given
        AccountSaveRequest accountSaveRequest=new AccountSaveRequest("예금계좌","00"
                ,null,"1234",null);
        PetAccount expectedNormalPetAccount = AccountFixture.NORMAL.getAccount();

        //when
        given(accountService.registerGeneralAccount(any(Long.class),any(AccountSaveRequest.class))).willReturn(expectedNormalPetAccount);

        // then
        ResultActions perform=mockMvc.perform(
                        post("/signup").contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(accountSaveRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.memberId").value("김민지"))
                .andExpect(jsonPath("$.data.email").value("minji@naver.com"))
                .andExpect(jsonPath("$.data.password").value("1234"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-1234"))
                .andExpect(jsonPath("$.data.birth").value("2004-05-07"))
                .andExpect(jsonPath("$.data.pinNumber").value("000000"))
                .andDo(print()).andDo(document("member-signup",getDocumentRequest(),getDocumentResponse()));
    }
}