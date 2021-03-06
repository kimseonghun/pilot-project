package com.woowabros.pilotproject.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CouponCreateRequestDto {

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issuableDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime usableDate;

    private Integer price;

    private Integer amount;

    @Builder
    public CouponCreateRequestDto(String name, LocalDateTime issuableDate, LocalDateTime usableDate, Integer price, Integer amount) {
        this.name = name;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
        this.amount = amount;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .name(name)
                .issuableDate(issuableDate)
                .usableDate(usableDate)
                .price(price)
                .amount(amount)
                .build();
    }
}
