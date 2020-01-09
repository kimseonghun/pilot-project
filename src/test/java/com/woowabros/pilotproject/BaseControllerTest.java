package com.woowabros.pilotproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest
public class BaseControllerTest {
    protected static final String LOGIN_NAME = "loginName";
    protected static final String LOGIN_PASSWORD = "loginPassword";

    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp(WebApplicationContext webApplicationContext,
                         RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    protected String getUrl(Class<?> clazz) {
        return linkTo(clazz).toString();
    }

    protected void preCreateMember() throws Exception {
        mockMvc.perform(post("/member/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("memberName", LOGIN_NAME)
                .param("password", LOGIN_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
