package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final CouponInnerService couponInnerService;
    private final IssuedCouponService issuedCouponService;

    public CouponService(CouponInnerService couponInnerService, IssuedCouponService issuedCouponService) {
        this.couponInnerService = couponInnerService;
        this.issuedCouponService = issuedCouponService;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        Coupon newCoupon = couponInnerService.save(coupon);

        for (int i = 0; i < coupon.getAmount(); i++) {
            issuedCouponService.create(newCoupon);
        }

        return newCoupon;
    }

    public CouponResponseDto findById(Long id) {
        Coupon coupon = couponInnerService.findById(id);

        return CouponResponseDto.builder()
                .issuableDate(coupon.getIssuableDate())
                .usableDate(coupon.getUsableDate())
                .price(coupon.getPrice())
                .build();
    }

    public List<CouponResponseDto> findAllByPageable(Pageable pageable) {
        return couponInnerService.findAll(pageable)
                .map(this::dto)
                .toList();
    }

    public List<CouponResponseDto> findIssuableCoupons() {
        return couponInnerService.findAll().stream()
                .filter(Coupon::isIssuableDate)
                .map(this::dto)
                .collect(Collectors.toList());
    }

    private CouponResponseDto dto(Coupon coupon) {
        return CouponResponseDto.builder()
                .id(coupon.getId())
                .issuableDate(coupon.getIssuableDate())
                .usableDate(coupon.getUsableDate())
                .price(coupon.getPrice())
                .build();
    }
}
