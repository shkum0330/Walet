package com.ssafy.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
@WebMvcTest
public class RestApiTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    protected String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    @BeforeEach
    public void setMockMvc(
            WebApplicationContext context, RestDocumentationContextProvider provider) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .apply(
                                documentationConfiguration(provider)
                                        .uris()
                                        .withScheme("http")
                                        .withHost("localhost") // 나중에 서버 url로 바꿔야됨
                                        .withPort(8083))
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .alwaysDo(print())
                        .alwaysDo(document("api/docs"))
                        .build();
    }
}
