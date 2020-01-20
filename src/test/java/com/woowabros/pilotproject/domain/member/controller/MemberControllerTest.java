package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class MemberControllerTest extends BaseControllerTest {
    private static final String MEMBER_URI = getUrl(MemberController.class);

    @Test
    void 회원_생성_테스트() {
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
                                parameterWithName("memberName").description("회원 계정 아이디"),
                                parameterWithName("password").description("회원 계정 비밀번호")
                        )));
    }

    @Test
    void 로그인_테스트() {
        login()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("member/login",
                        requestParameters(
                                parameterWithName("memberName").description("로그인할 회원 계정 아이디"),
                                parameterWithName("password").description("로그인할 회원 계정 비밀번호")
                        )));
    }
}