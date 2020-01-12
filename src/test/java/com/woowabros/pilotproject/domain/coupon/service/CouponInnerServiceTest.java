package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.coupon.exception.NotFoundCouponException;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponInnerServiceTest {

    @InjectMocks
    private CouponInnerService couponInnerService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 쿠폰_생성_테스트() {
        // given
        Coupon coupon = mock(Coupon.class);
        given(couponRepository.save(any())).willReturn(coupon);

        // when & then
        assertThat(couponInnerService.save(coupon)).isEqualTo(coupon);
        verify(couponRepository, times(1)).save(any());
    }

    @Test
    void 쿠폰_단건_조회_테스트() {
        // given
        Coupon coupon = mock(Coupon.class);
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        // when & then
        assertThat(couponInnerService.findById(1L)).isEqualTo(coupon);
        verify(couponRepository, times(1)).findById(anyLong());
    }

    @Test
    void 없는_쿠폰_조회_시_예외_테스트() {
        // given
        given(couponRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundCouponException.class, () -> couponInnerService.findById(1L));
        verify(couponRepository, times(1)).findById(anyLong());
    }

    @Test
    void 현재_사용가능한_쿠폰_조회_테스트() {
        // given
        Coupon coupon = Coupon.builder()
                .usableDate(DateUtil.tomorrow())
                .build();
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        // when & then
        assertThat(couponInnerService.findUsableCouponById(1L)).isEqualTo(coupon);
        verify(couponRepository, times(1)).findById(anyLong());
    }

    @Test
    void 현재_사용할_수_없는_쿠폰_조회_시_예외_테스트() {
        // given
        Coupon coupon = Coupon.builder()
                .usableDate(DateUtil.yesterday())
                .build();
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        // when & then
        assertThrows(NotFoundCouponException.class, () -> couponInnerService.findUsableCouponById(1L));
        verify(couponRepository, times(1)).findById(anyLong());
    }

    @Test
    void 쿠폰_페이징_다건_조회_테스트() {
        // given
        Coupon coupon = mock(Coupon.class);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Coupon> coupons = new PageImpl<>(
                IntStream.range(0, 5)
                        .mapToObj(index -> coupon)
                        .collect(Collectors.toList())
        );
        given(couponInnerService.findAll(pageable)).willReturn(coupons);

        // when & then
        assertThat(couponInnerService.findAll(pageable)).isEqualTo(coupons);
        verify(couponRepository, times(1)).findAll(pageable);
    }

    @Test
    void 쿠폰_전체_다건_조회_테스트() {
        // given
        List<Coupon> coupons = Collections.singletonList(mock(Coupon.class));
        given(couponRepository.findAll()).willReturn(coupons);

        // when & then
        assertThat(couponInnerService.findAll()).isEqualTo(coupons);
        verify(couponRepository, times(1)).findAll();
    }
}