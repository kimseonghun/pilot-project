package com.woowabros.pilotproject.domain.issuedcoupon.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class IssuedCouponApiControllerTest extends BaseControllerTest {
    private static final String ISSUED_COUPON_URI = getUrl(IssuedCouponApiController.class);

    private IssuedCouponResponseDto dto = IssuedCouponResponseDto.builder()
            .couponName("페리카나 3000원 쿠폰")
            .couponCode("1234")
            .couponPrice(3000)
            .build();

    @Test
    void 사용가능한_쿠폰_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(ISSUED_COUPON_URI + "/usable")
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(IssuedCouponResponseDto.class).contains(dto)
                .consumeWith(document("issuedCoupon/findUsableCouponsByMemberId",
                        responseFields(
                                fieldWithPath("[].couponCode").type(JsonFieldType.STRING).description("쿠폰 고유 코드"),
                                fieldWithPath("[].couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("[].couponPrice").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                        )));
    }

    @Test
    void 쿠폰_발급_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(ISSUED_COUPON_URI + "/{couponId}", 2L)
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBody()
                .consumeWith(document("issuedCoupon/issue",
                        pathParameters(
                                parameterWithName("couponId").description("발급받을 쿠폰의 고유 번호")
                        )));
    }
}