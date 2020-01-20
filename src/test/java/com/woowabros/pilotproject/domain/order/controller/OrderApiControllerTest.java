package com.woowabros.pilotproject.domain.order.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
                .paymentType(PaymentType.SIMPLICITY.getName())
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
    void 쿠폰_취소_테스트() {
        // when
        WebTestClient.ResponseSpec result = webTestClient.delete()
                .uri(ORDER_URI + "/{orderId}", 1L)
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isNotFound()
                .expectBody()
                .consumeWith(document("order/cancel",
                        pathParameters(
                                parameterWithName("orderId").description("취소할 주문 고유 번호")
                        )));
    }
}