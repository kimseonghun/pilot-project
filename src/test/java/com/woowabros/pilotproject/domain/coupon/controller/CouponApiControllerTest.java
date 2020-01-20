package com.woowabros.pilotproject.domain.coupon.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;


class CouponApiControllerTest extends BaseControllerTest {
    private static final String COUPON_URI = getUrl(CouponApiController.class);

    private CouponResponseDto couponResponseDto = CouponResponseDto.builder()
            .id(1L)
            .name("페리카나 3000원 쿠폰")
            .issuableDate(LocalDateTime.parse("2021-07-12T22:47:34"))
            .usableDate(LocalDateTime.parse("2022-07-12T22:47:34"))
            .price(3000)
            .build();

    @Test
    void 어드민_쿠폰_생성_테스트() {
        // given
        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("페리카나 쿠폰")
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(10))
                .price(3000)
                .amount(100)
                .build();

        // when
        WebTestClient.ResponseSpec result = webTestClient.post()
                .uri(COUPON_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBody()
                .consumeWith(document("coupon/create",
                        requestFields(
                                fieldWithPath("name").description("쿠폰 이름"),
                                fieldWithPath("issuableDate").description("발급 가능 날짜"),
                                fieldWithPath("usableDate").description("사용 가능 날짜"),
                                fieldWithPath("price").description("쿠폰 할인 금액"),
                                fieldWithPath("amount").description("쿠폰 수량")
                        )));
    }

    @Test
    void 어드민_쿠폰_다건_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(COUPON_URI)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(CouponResponseDto.class).contains(couponResponseDto)
                .consumeWith(document("coupon/findByPageable",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쿠폰 고유 번호"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("[].issuableDate").type(JsonFieldType.VARIES).description("쿠폰 발급 가능 날짜"),
                                fieldWithPath("[].usableDate").type(JsonFieldType.VARIES).description("쿠폰 사용 가능 날짜"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                        )));
    }

    @Test
    void 어드민_쿠폰_단건_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(COUPON_URI + "/{couponId}", 1L)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBody(CouponResponseDto.class).isEqualTo(couponResponseDto)
                .consumeWith(document("coupon/findById",
                        pathParameters(
                                parameterWithName("couponId").description("조회할 쿠폰 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("쿠폰 고유 번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("issuableDate").type(JsonFieldType.VARIES).description("쿠폰 발급 가능 날짜"),
                                fieldWithPath("usableDate").type(JsonFieldType.VARIES).description("쿠폰 사용 가능 날짜"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                        )));
    }

    @Test
    void 발급할_수_있는_쿠폰_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(COUPON_URI + "/issuable")
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(CouponResponseDto.class).contains(couponResponseDto)
                .consumeWith(document("coupon/findIssuableCoupons",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쿠폰 고유 번호"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("[].issuableDate").type(JsonFieldType.VARIES).description("쿠폰 발급 가능 날짜"),
                                fieldWithPath("[].usableDate").type(JsonFieldType.VARIES).description("쿠폰 사용 가능 날짜"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                        )));
    }
}