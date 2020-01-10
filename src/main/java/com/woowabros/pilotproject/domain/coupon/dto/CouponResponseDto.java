package com.woowabros.pilotproject.domain.coupon.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponResponseDto {
    private Long id;
    private Date issuableDate;
    private Date usableDate;
    private Integer price;

    @Builder
    public CouponResponseDto(Long id, Date issuableDate, Date usableDate, Integer price) {
        this.id = id;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
    }
}
