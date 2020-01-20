package com.woowabros.pilotproject.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponResponseDto {
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issuableDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime usableDate;

    private Integer price;

    @Builder
    public CouponResponseDto(Long id, String name, LocalDateTime issuableDate, LocalDateTime usableDate, Integer price) {
        this.id = id;
        this.name = name;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
    }
}
