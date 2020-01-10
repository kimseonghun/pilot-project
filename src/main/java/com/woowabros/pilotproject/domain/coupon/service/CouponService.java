package com.woowabros.pilotproject.domain.coupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.domain.CouponRepository;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 쿠폰은 존재하지 않습니다."));

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

    private CouponResponseDto dto(Coupon coupon) {
        return CouponResponseDto.builder()
                .id(coupon.getId())
                .issuableDate(coupon.getIssuableDate())
                .usableDate(coupon.getUsableDate())
                .price(coupon.getPrice())
                .build();
    }
}
