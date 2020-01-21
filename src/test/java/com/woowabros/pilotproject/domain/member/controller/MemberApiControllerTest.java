package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.member.dto.MemberCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class MemberApiControllerTest extends BaseControllerTest {
    private static final String MEMBER_URI = getUrl(MemberApiController.class);

    @Test
    void 회원_생성_테스트() {
        // given
        MemberCreateDto dto = MemberCreateDto.builder()
                .memberName("memberName")
                .password("password")
                .build();

        WebTestClient.ResponseSpec result = webTestClient.post()
                .uri(MEMBER_URI + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange();

        result
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("member/save",
                        requestFields(
                                fieldWithPath("memberName").description("회원 계정 아이디"),
                                fieldWithPath("password").description("회원 계정 비밀번호")
                        )));
    }

    @Test
    void 로그인_테스트() {
        login()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("member/login",
                        requestFields(
                                fieldWithPath("memberName").description("로그인할 회원 계정 아이디"),
                                fieldWithPath("password").description("로그인할 회원 계정 비밀번호")
                        )));
    }
}