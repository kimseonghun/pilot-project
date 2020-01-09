package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends BaseControllerTest {
    private final String uri = getUrl(MemberController.class);

    @Test
    void 회원_생성_테스트() throws Exception {
        mockMvc.perform(post(uri + "/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("memberName", "memberName")
                .param("password", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/save",
                        requestParameters(
                                attributes(key("title").value("Fields for member creation")),
                                parameterWithName("memberName")
                                        .description("회원 계정 아이디")
                                        .attributes(key("constraints").value("null이나 공백 허용 불가")),
                                parameterWithName("password")
                                        .description("회원 계정 비밀번호")
                                        .attributes(key("constraints").value("null이나 공백 허용 불가"))
                        )));
    }

    @Test
    void 로그인_테스트() throws Exception {
        preCreateMember();

        mockMvc.perform(post(uri + "/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("memberName", LOGIN_NAME)
                .param("password", LOGIN_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/login",
                        requestParameters(
                                attributes(key("title").value("Fields for member creation")),
                                parameterWithName("memberName")
                                        .description("로그인할 회원 계정 아이디")
                                        .attributes(key("constraints").value("null이나 공백 허용 불가")),
                                parameterWithName("password")
                                        .description("로그인할 회원 계정 비밀번호")
                                        .attributes(key("constraints").value("null이나 공백 허용 불가"))
                        )));
    }
}