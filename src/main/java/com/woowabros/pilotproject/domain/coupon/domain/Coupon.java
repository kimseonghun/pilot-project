package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.coupon.exception.CannotCreateCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    private static final int EXHAUSTED_COUPON_AMOUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime issuableDate;

    @Column(nullable = false)
    private LocalDateTime usableDate;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "coupon")
    private List<IssuedCoupon> issuedCoupons = new ArrayList<>();

    @Builder
    public Coupon(String name, LocalDateTime issuableDate, LocalDateTime usableDate, int price, int amount) {
        if (issuableDate.isAfter(usableDate)) {
            throw new CannotCreateCouponException();
        }

        this.name = name;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
        this.amount = amount;
    }

    public boolean isIssuableDate() {
        return LocalDateTime.now().isBefore(issuableDate);
    }

    public boolean isIssuableAmount() {
        return this.amount > EXHAUSTED_COUPON_AMOUNT;
    }

    public boolean isUsableDate() {
        return LocalDateTime.now().isBefore(usableDate);
    }

    public Integer subtractAmount() {
        if (!isIssuableAmount()) {
            throw new NotIssuableCouponException();
        }
        return --this.amount;
    }
}
