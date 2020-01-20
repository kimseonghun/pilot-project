package com.woowabros.pilotproject.domain.menu.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class MenuApiControllerTest extends BaseControllerTest {
    private static final String MENU_URI = getUrl(MenuApiController.class);

    private MenuResponseDto dto = MenuResponseDto.builder()
            .menuId(1L)
            .menuName("chicken")
            .menuPrice(20000)
            .build();

    @Test
    void 메뉴_전체_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(MENU_URI)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(MenuResponseDto.class).contains(dto)
                .consumeWith(document("menu/findAll",
                        responseFields(
                                fieldWithPath("[].menuId").type(JsonFieldType.NUMBER).description("메뉴 고유 번호"),
                                fieldWithPath("[].menuName").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("[].menuPrice").type(JsonFieldType.NUMBER).description("메뉴 가격")
                        )));
    }
}