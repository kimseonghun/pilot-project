package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.converter.CouponStatusAttributeConverter;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponStatus;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotUsableCouponException;
import com.woowabros.pilotproject.domain.member.domain.Member;
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

    @Convert(converter = CouponStatusAttributeConverter.class)
    private CouponStatus status;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_coupon_to_issued_coupon"))
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_issued_coupon"))
    private Member member;

    @Builder
    public IssuedCoupon(String couponCode, Coupon coupon, Member member) {
        this.couponCode = CouponCode.of(couponCode);
        this.status = CouponStatus.ISSUABLE;
        this.member = member;
        createCoupon(coupon);
    }

    private void createCoupon(Coupon coupon) {
        coupon.getIssuedCoupons().add(this);
        this.coupon = coupon;
    }

    public boolean isIssuableStatus() {
        return CouponStatus.ISSUABLE.equals(this.status);
    }

    public IssuedCoupon issueTo(Member member) {
        if (!isIssuableStatus()) {
            throw new NotIssuableCouponException();
        }

        this.member = member;
        this.status = CouponStatus.USABLE;
        this.coupon.subtractAmount();
        return this;
    }

    public IssuedCoupon use() {
        if (!this.status.equals(CouponStatus.USABLE)) {
            throw new NotUsableCouponException();
        }

        this.status = CouponStatus.COMPLETED;
        return this;
    }
}
