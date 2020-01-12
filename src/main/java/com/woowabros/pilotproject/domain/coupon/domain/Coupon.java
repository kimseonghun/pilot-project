package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
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
        return new Date().before(issuableDate);
    }

    public boolean isUsableDate() {
        return new Date().before(usableDate);
    }
}
