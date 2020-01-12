package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.coupon.exception.NotFoundCouponException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponInnerService {
    private final CouponRepository couponRepository;

    public CouponInnerService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon findById(Long id) {
        return couponRepository.findById(id).orElseThrow(NotFoundCouponException::new);
    }

    public Coupon findUsableCouponById(Long id) {
        return couponRepository.findById(id)
                .filter(Coupon::isUsableDate)
                .orElseThrow(NotFoundCouponException::new);
    }

    public Page<Coupon> findAll(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }
}
