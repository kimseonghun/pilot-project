package com.woowabros.pilotproject.domain.issuedcoupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCouponRepository;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssuedCouponService {
    private final IssuedCouponRepository issuedCouponRepository;

    public IssuedCouponService(IssuedCouponRepository issuedCouponRepository) {
        this.issuedCouponRepository = issuedCouponRepository;
    }

    @Transactional
    public IssuedCoupon issue(Coupon coupon) {
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode(generateCouponCode())
                .coupon(coupon)
                .build();

        return issuedCouponRepository.save(issuedCoupon);
    }

    private String generateCouponCode() {
        String code = UUID.randomUUID().toString();
        Optional<IssuedCoupon> issuedCoupon = issuedCouponRepository.findByCouponCode(CouponCode.of(code));

        if (issuedCoupon.isPresent()) {
            generateCouponCode();
        }

        return code;
    }
}
