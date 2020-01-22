package com.woowabros.pilotproject.domain.order.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class OrderApiControllerTest extends BaseControllerTest {
    private static final String ORDER_URI = getUrl(OrderApiController.class);

    @Test
    void 주문_생성_테스트() {
        // given
        Long chickenId = 1L;
        Long pizzaId = 2L;
        Long hamburgerId = 3L;
        String couponCode = "9876";

        OrderCreateRequestDto orderCreateRequestDto = OrderCreateRequestDto.builder()
                .paymentType(PaymentType.SIMPLICITY)
                .menuIds(Arrays.asList(chickenId, pizzaId, hamburgerId))
                .couponCodes(Collections.singletonList(couponCode))
                .build();

        // when
        WebTestClient.ResponseSpec result = webTestClient.post()
                .uri(ORDER_URI)
                .header(COOKIE, cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderCreateRequestDto)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBody()
                .consumeWith(document("order/create",
                        requestFields(
                                fieldWithPath("menuIds").description("주문할 메뉴 고유 번호"),
                                fieldWithPath("paymentType").description("결제 수단"),
                                fieldWithPath("couponCodes").description("사용할 실제 쿠폰 고유 코드")
                        )));
    }

    @Test
    void 주_취소_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.delete()
                .uri(ORDER_URI + "/{orderId}", 1L)
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBody()
                .consumeWith(document("order/cancel",
                        pathParameters(
                                parameterWithName("orderId").description("취소할 주문 고유 번호")
                        )));
    }

    @Test
    void 주문_내역_전체_조회_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(ORDER_URI)
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(OrderResponseDto.class).hasSize(4)
                .consumeWith(document("order/findAll",
                        responseFields(
                                fieldWithPath("[].orderId").type(JsonFieldType.NUMBER).description("주문 고유 번호"),
                                fieldWithPath("[].menus[].menuId").type(JsonFieldType.NUMBER).description("메뉴 고유 번호"),
                                fieldWithPath("[].menus[].menuName").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("[].menus[].menuPrice").type(JsonFieldType.NUMBER).description("메뉴 금액"),
                                fieldWithPath("[].paymentType").type(JsonFieldType.STRING).description("결제 수단"),
                                fieldWithPath("[].orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
                                fieldWithPath("[].coupons[].couponCode").type(JsonFieldType.STRING).description("쿠폰 고유 코드"),
                                fieldWithPath("[].coupons[].couponName").type(JsonFieldType.STRING).description("쿠폰 고유 코드"),
                                fieldWithPath("[].coupons[].couponPrice").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액"),
                                fieldWithPath("[].totalPrice").type(JsonFieldType.NUMBER).description("총 구매 금액"),
                                fieldWithPath("[].totalDiscountPrice").type(JsonFieldType.NUMBER).description("총 할인 금액")
                        )));
    }
}