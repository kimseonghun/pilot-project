package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.coupon.exception.NotFoundCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public CouponResponseDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(NotFoundCouponException::new);

        return CouponResponseDto.builder()
                .issuableDate(coupon.getIssuableDate())
                .usableDate(coupon.getUsableDate())
                .price(coupon.getPrice())
                .build();
    }

    public List<CouponResponseDto> findAllByPageable(Pageable pageable) {
        return couponRepository.findAll(pageable)
                .map(this::dto)
                .toList();
    }

    public List<CouponResponseDto> findIssuableCoupons() {
        return couponRepository.findAll().stream()
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
