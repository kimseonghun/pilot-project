package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

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

    @Test
    void 쿠폰_저장_테스트() {
        // given
        Coupon coupon = Coupon.builder()
                .issuableDate(mock(Date.class))
                .usableDate(mock(Date.class))
                .price(1000)
                .amount(10)
                .build();
        given(couponRepository.save(coupon)).willReturn(coupon);
        given(issuedCouponService.issue(coupon)).willReturn(any());

        // when
        Coupon newCoupon = couponService.save(coupon);

        // then
        assertThat(newCoupon).isEqualTo(coupon);
        verify(couponRepository, times(1)).save(any());
        verify(issuedCouponService, times(10)).issue(any());
    }
}