package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.converter.CouponIssuedTypeAttributeConverter;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponIssuedType;
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

    @Convert(converter = CouponIssuedTypeAttributeConverter.class)
    private CouponIssuedType status;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_coupon_to_issued_coupon"))
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_issued_coupon"))
    private Member member;

    @Builder
    public IssuedCoupon(String couponCode, Coupon coupon, Member member) {
        this.couponCode = CouponCode.of(couponCode);
        this.status = CouponIssuedType.ISSUABLE;
        this.coupon = coupon;
        this.member = member;
    }
}
