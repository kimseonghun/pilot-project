package com.woowabros.pilotproject.domain.coupon.controller;

import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponApiController {
    private final CouponService couponService;

    public CouponApiController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponseDto> create(@RequestBody CouponCreateRequestDto coupontDto) {
        couponService.create(coupontDto.toEntity());
        return ResponseEntity.ok().build();
    }
}
