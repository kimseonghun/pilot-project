package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    private Date issuableDate;

    @Column(nullable = false)
    private Date usableDate;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "coupon")
    private List<IssuedCoupon> issuedCoupons = new ArrayList<>();

    @Builder
    public Coupon(String name, Date issuableDate, Date usableDate, int price, int amount) {
        this.name = name;
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
        this.amount = amount;
    }

    public boolean isIssuableDate() {
        return new Date().before(issuableDate) && this.amount > EXHAUSTED_COUPON_AMOUNT;
    }

    public boolean isUsableDate() {
        return new Date().before(usableDate);
    }

    public Integer subtractAmount() {
        if (this.amount == EXHAUSTED_COUPON_AMOUNT) {
            throw new NotIssuableCouponException();
        }
        return --this.amount;
    }
}
