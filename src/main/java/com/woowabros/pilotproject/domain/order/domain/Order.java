package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.order.domain.converter.OrderStatusAttributeConverter;
import com.woowabros.pilotproject.domain.order.domain.converter.PaymentTypeAttributeConverter;
import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.exception.CannotCancelOrderException;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer totalPrice;

    @Column
    private Integer totalDiscountPrice;

    @Convert(converter = OrderStatusAttributeConverter.class)
    private OrderStatus status;

    @Convert(converter = PaymentTypeAttributeConverter.class)
    private PaymentType payment;

    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_member_to_orders"))
    private Member member;

    @Builder
    public Order(Integer totalPrice, Integer totalDiscountPrice, PaymentType payment, Member member) {
        this.totalPrice = totalPrice;
        this.totalDiscountPrice = totalDiscountPrice;
        this.payment = payment;
        this.member = member;
        this.status = OrderStatus.COMPLETED;
    }

    public Order calculateTotalPrice(List<OrderMenu> orderMenus) {
        this.totalPrice = orderMenus.stream()
                .map(orderMenu -> orderMenu.getMenu().getPrice())
                .reduce(0, Integer::sum);

        return this;
    }

    public Order calculateTotalDiscountPrice(List<IssuedCoupon> usedCoupons) {
        this.totalDiscountPrice = usedCoupons.stream()
                .map(issuedCoupon -> issuedCoupon.getCoupon().getPrice())
                .reduce(0, Integer::sum);

        return this;
    }

    public Order cancel(Member member) {
        if (OrderStatus.CANCEL.equals(this.status)) {
            throw new CannotCancelOrderException("이미 취소된 주문입니다.");
        }

        if (!this.member.equals(member)) {
            throw new CannotCancelOrderException("주문한 본인만이 취소할 수 있습니다.");
        }

        this.status = OrderStatus.CANCEL;
        return this;
    }
}
