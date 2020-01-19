package com.woowabros.pilotproject.domain.coupon.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.woowabros.pilotproject.BaseControllerTest;
import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponApiControllerTest extends BaseControllerTest {
    private static final String COUPON_URI = getUrl(CouponApiController.class);

    private CouponResponseDto couponResponseDto = CouponResponseDto.builder()
            .id(1L)
            .name("페리카나 3000원 쿠폰")
            .issuableDate(LocalDateTime.parse("2021-07-12T22:47:34.158"))
            .usableDate(LocalDateTime.parse("2022-07-12T22:47:34.158"))
            .price(3000)
            .build();

    @Test
    void 어드민_쿠폰_생성_테스트() throws Exception {
        // given
        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("페리카나 쿠폰")
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(10))
                .price(3000)
                .amount(100)
                .build();

        // when
        ResultActions result = mockMvc.perform(post(COUPON_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dto)))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(handler().handlerType(CouponApiController.class))
                .andExpect(handler().methodName("create"))
                .andDo(document("coupon/create",
                        requestFields(
                                fieldWithPath("name").description("쿠폰 이름"),
                                fieldWithPath("issuableDate").description("발급 가능 날짜"),
                                fieldWithPath("usableDate").description("사용 가능 날짜"),
                                fieldWithPath("price").description("쿠폰 할인 금액"),
                                fieldWithPath("amount").description("쿠폰 수량")
                        )));
    }

    @Test
    void 어드민_쿠폰_단건_조회_테스트() throws Exception {
        // when
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get(COUPON_URI + "/{couponId}", 1L)).andDo(print());

        // then
        String response = result.andExpect(status().isOk())
                .andExpect(handler().handlerType(CouponApiController.class))
                .andExpect(handler().methodName("findById"))
                .andReturn().getResponse().getContentAsString();

        assertThat(OBJECT_MAPPER.readValue(response, CouponResponseDto.class)).isEqualTo(couponResponseDto);

        result.andDo(document("coupon/findById",
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
    void 어드민_쿠폰_다건_조회_테스트() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResultActions result = mockMvc.perform(get(COUPON_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(pageable)))
                .andDo(print());

        // then
        String response = result.andExpect(status().isOk())
                .andExpect(handler().handlerType(CouponApiController.class))
                .andExpect(handler().methodName("findByPageable"))
                .andReturn().getResponse().getContentAsString();

        List<CouponResponseDto> couponResponseDtos = OBJECT_MAPPER.readValue(response, new TypeReference<List<CouponResponseDto>>() {
        });

        assertThat(couponResponseDtos).contains(couponResponseDto);

        result.andDo(document("coupon/findByPageable",
                requestFields(
                        fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("출력할 페이지 번호").optional(),
                        fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("한 번에 출력할 페이지의 크기").optional(),
                        fieldWithPath("offset").type(JsonFieldType.NUMBER).ignored(),
                        fieldWithPath("paged").type(JsonFieldType.BOOLEAN).ignored(),
                        fieldWithPath("unpaged").type(JsonFieldType.BOOLEAN).ignored(),
                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).ignored(),
                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).ignored(),
                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).ignored()
                ),
                responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쿠폰 고유 번호"),
                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                        fieldWithPath("[].issuableDate").type(JsonFieldType.VARIES).description("쿠폰 발급 가능 날짜"),
                        fieldWithPath("[].usableDate").type(JsonFieldType.VARIES).description("쿠폰 사용 가능 날짜"),
                        fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                )));
    }

    @Test
    void 발급할_수_있는_쿠폰_조회_테스트() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get(COUPON_URI + "/issuable")).andDo(print());

        // then
        String response = result.andExpect(status().isOk())
                .andExpect(handler().handlerType(CouponApiController.class))
                .andExpect(handler().methodName("findIssuableCoupons"))
                .andReturn().getResponse().getContentAsString();

        List<CouponResponseDto> couponResponseDtos = OBJECT_MAPPER.readValue(response, new TypeReference<List<CouponResponseDto>>() {
        });

        assertThat(couponResponseDtos).contains(couponResponseDto);

        result.andDo(document("coupon/findIssuableCoupons",
                responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쿠폰 고유 번호"),
                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                        fieldWithPath("[].issuableDate").type(JsonFieldType.VARIES).description("쿠폰 발급 가능 날짜"),
                        fieldWithPath("[].usableDate").type(JsonFieldType.VARIES).description("쿠폰 사용 가능 날짜"),
                        fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
                )));
    }
}