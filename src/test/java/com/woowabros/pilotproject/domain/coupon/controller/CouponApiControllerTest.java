package com.woowabros.pilotproject.domain.coupon.controller;

import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponApiControllerTest extends BaseControllerTest {
    private static final String COUPON_URI = getUrl(CouponApiController.class);

    @Test
    void 쿠폰_생성_테스트() throws Exception {
        // given
        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("페리카나 쿠폰")
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(10))
                .price(3000)
                .amount(100)
                .build();

        System.out.println("OBJECT_MAPPER.writeValueAsString(dto) = " + OBJECT_MAPPER.writeValueAsString(dto));
        System.out.println("issuableDate = " + dto.getIssuableDate());
        // when
        ResultActions result = mockMvc.perform(post(COUPON_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dto)))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andDo(document("coupon/create",
                        requestFields(
                                fieldWithPath("name").description("쿠폰 이름"),
                                fieldWithPath("issuableDate").description("발급 가능 날짜"),
                                fieldWithPath("usableDate").description("사용 가능 날짜"),
                                fieldWithPath("price").description("쿠폰 할인 금액"),
                                fieldWithPath("amount").description("쿠폰 수량")
                        )));
    }
}