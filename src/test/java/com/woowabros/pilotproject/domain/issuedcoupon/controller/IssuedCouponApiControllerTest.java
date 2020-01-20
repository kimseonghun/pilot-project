package com.woowabros.pilotproject.domain.issuedcoupon.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.COOKIE;

class IssuedCouponApiControllerTest extends BaseControllerTest {
    private static final String ISSUED_COUPON_URI = getUrl(IssuedCouponApiController.class);

    private IssuedCouponResponseDto dto = IssuedCouponResponseDto.builder()
            .couponName("페리카나 3000원 쿠폰")
            .couponCode("1234")
            .couponPrice(3000)
            .build();

    @Test
    void 사용가능한_쿠폰_조회_테스트() throws Exception {
        // given
        String cookie = login()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");


        // when
//        ResultActions result = mockMvc.perform(get(ISSUED_COUPON_URI + "/usable").header(COOKIE, cookie)).andDo(print());
        WebTestClient.ResponseSpec result = webTestClient.get()
                .uri(ISSUED_COUPON_URI + "/usable")
                .header(COOKIE, cookie)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectBodyList(IssuedCouponResponseDto.class).contains(dto);
//        String response = result.andExpect(status().isOk())
//                .andExpect(handler().handlerType(IssuedCouponApiController.class))
//                .andExpect(handler().methodName("findUsableCouponsByMemberId"))
//                .andReturn().getResponse().getContentAsString();
//
//        List<IssuedCouponResponseDto> issuedCouponResponseDtos = OBJECT_MAPPER.readValue(response, new TypeReference<List<IssuedCouponResponseDto>>() {
//        });
//
//        assertThat(issuedCouponResponseDtos).contains(dto);
    }
}