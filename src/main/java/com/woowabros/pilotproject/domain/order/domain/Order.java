package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
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

    @Column(nullable = false)
    private Integer totalPrice;

    @Convert(converter = OrderStatusAttributeConverter.class)
    private OrderStatus status;

    @Convert(converter = PaymentTypeAttributeConverter.class)
    private PaymentType payment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_member_to_orders"))
    private Member member;

    @Builder
    public Order(Integer totalPrice, PaymentType payment, Member member) {
        this.totalPrice = totalPrice;
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

    public Order cancel() {
        if (OrderStatus.CANCEL.equals(this.status)) {
            throw new CannotCancelOrderException();
        }

        this.status = OrderStatus.CANCEL;
        return this;
    }
}
