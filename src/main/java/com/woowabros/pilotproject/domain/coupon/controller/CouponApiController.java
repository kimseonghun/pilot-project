package com.woowabros.pilotproject.domain.coupon.controller;

import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.coupon.service.CouponService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponseDto> findById(@PathVariable Long couponId) {
        CouponResponseDto response = couponService.findById(couponId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> findByPageable(Pageable pageable) {
        List<CouponResponseDto> response = couponService.findAllByPageable(pageable);
        return ResponseEntity.ok(response);
    }
}
