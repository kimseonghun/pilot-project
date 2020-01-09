package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final IssuedCouponService issuedCouponService;

    public CouponService(CouponRepository couponRepository, IssuedCouponService issuedCouponService) {
        this.couponRepository = couponRepository;
        this.issuedCouponService = issuedCouponService;
    }

    public Coupon save(Coupon coupon) {
        Coupon newCoupon = couponRepository.save(coupon);

        for (int i = 0; i < coupon.getAmount(); i++) {
            issuedCouponService.issue(newCoupon);
        }

        return newCoupon;
    }
}
