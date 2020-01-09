package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuedCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(
            name = "code",
            column = @Column(unique = true, nullable = false))
    private CouponCode couponCode;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_coupon_to_issued_coupon"))
    private Coupon coupon;

    @Builder
    public IssuedCoupon(String couponCode, Coupon coupon) {
        this.couponCode = CouponCode.of(couponCode);
        this.coupon = coupon;
    }
}
