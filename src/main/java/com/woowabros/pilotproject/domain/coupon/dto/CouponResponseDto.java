package com.woowabros.pilotproject.domain.coupon.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponResponseDto {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issuableDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime usableDate;

    private Integer price;

    @Builder
    public CouponResponseDto(Long id, LocalDateTime issuableDate, LocalDateTime usableDate, Integer price) {
        this.id = id;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
    }
}
