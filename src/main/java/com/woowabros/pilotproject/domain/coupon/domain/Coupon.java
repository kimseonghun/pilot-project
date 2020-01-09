package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date issuableDate;

    @Column(nullable = false)
    private Date usableDate;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer amount;

    @Builder
    public Coupon(Date issuableDate, Date usableDate, int price, int amount) {
        this.issuableDate = issuableDate;
        this.usableDate = usableDate;
        this.price = price;
        this.amount = amount;
    }

    public boolean isPossibleToRegister() {
        return new Date().before(issuableDate);
    }

    public boolean isPossibleToUse() {
        return new Date().before(usableDate);
    }
}
