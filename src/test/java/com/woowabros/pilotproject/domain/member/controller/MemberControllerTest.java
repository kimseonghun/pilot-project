package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class MemberControllerTest extends BaseControllerTest {
    private static final String MEMBER_URI = getUrl(MemberController.class);

    @Test
    void 회원_생성_테스트2() {
        webTestClient.post()
                .uri(MEMBER_URI + "/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("memberName", "memberName")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("member/save",
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
        login()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("member/login",
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