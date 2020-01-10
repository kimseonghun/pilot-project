package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private IssuedCouponService issuedCouponService;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
                .issuableDate(mock(Date.class))
                .usableDate(mock(Date.class))
                .price(1000)
                .amount(10)
                .build();
    }

    @Test
    void 쿠폰_저장_테스트() {
        // given
        given(couponRepository.save(coupon)).willReturn(coupon);
        given(issuedCouponService.issue(coupon)).willReturn(any());

        // when
        Coupon newCoupon = couponService.save(coupon);

        // then
        assertThat(newCoupon).isEqualTo(coupon);
        verify(couponRepository, times(1)).save(any());
        verify(issuedCouponService, times(10)).issue(any());
    }

    @Test
    void 쿠폰_단건_조회_테스트() {
        // given
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        // when
        CouponResponseDto response = couponService.findById(1L);

        // then
        assertThat(response).isEqualTo(CouponResponseDto.builder()
                .issuableDate(coupon.getIssuableDate())
                .usableDate(coupon.getUsableDate())
                .price(coupon.getPrice())
                .build());
        verify(couponRepository, times(1)).findById(anyLong());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    void 쿠폰_페이징_다건_조회_테스트(int size) {
        // given
        Pageable pageable = PageRequest.of(0, size);
        Page<Coupon> coupons = new PageImpl<>(
                IntStream.range(0, size)
                        .mapToObj(index -> coupon)
                        .collect(Collectors.toList())
        );
        given(couponRepository.findAll(pageable)).willReturn(coupons);

        // when
        List<CouponResponseDto> response = couponService.findAllByPageable(pageable);

        // then
        assertThat(response)
                .hasSize(size)
                .allMatch(element -> element.getIssuableDate().equals(coupon.getIssuableDate()))
                .allMatch(element -> element.getUsableDate().equals(coupon.getUsableDate()))
                .allMatch(element -> element.getPrice().equals(coupon.getPrice()));
        verify(couponRepository, times(1)).findAll(pageable);
    }
}