package com.woowabros.pilotproject.domain.coupon.controller;

import com.woowabros.pilotproject.domain.common.domain.ErrorResponse;
import com.woowabros.pilotproject.domain.coupon.dto.CouponCreateRequestDto;
import com.woowabros.pilotproject.domain.coupon.dto.CouponResponseDto;
import com.woowabros.pilotproject.domain.coupon.service.CouponService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity create(@RequestBody CouponCreateRequestDto couponDto) {
        couponService.create(couponDto.toEntity());
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

    @GetMapping("/issuable")
    public ResponseEntity<List<CouponResponseDto>> findIssuableCoupons() {
        List<CouponResponseDto> response = couponService.findIssuableCoupons();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
