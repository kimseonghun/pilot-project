package com.woowabros.pilotproject.domain.issuedcoupon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class IssuedCouponResponseDto {
    private String couponCode;
    private String couponName;
    private Integer couponPrice;

    @Builder
    public IssuedCouponResponseDto(String couponCode, String couponName, Integer couponPrice) {
        this.couponCode = couponCode;
        this.couponName = couponName;
        this.couponPrice = couponPrice;
    }
}
