package com.woowabros.pilotproject.domain.coupon.dto;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CouponCreateRequestDto {
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issuableDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
