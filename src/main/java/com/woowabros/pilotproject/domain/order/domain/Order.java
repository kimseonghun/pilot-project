package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.order.domain.converter.OrderStatusAttributeConverter;
import com.woowabros.pilotproject.domain.order.domain.converter.PaymentTypeAttributeConverter;
import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import lombok.*;

import javax.persistence.*;

@Entity
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
    private Member member;

    @Builder
    public Order(Integer totalPrice, PaymentType payment, Member member) {
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.member = member;
        this.status = OrderStatus.COMPLETED;
    }
}
