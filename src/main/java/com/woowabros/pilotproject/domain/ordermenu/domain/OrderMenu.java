package com.woowabros.pilotproject.domain.ordermenu.domain;

import com.woowabros.pilotproject.domain.common.domain.BaseTimeEntity;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.order.domain.Order;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_to_order_menu"))
    private Order order;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_menu_to_order_menu"))
    private Menu menu;

    @Builder
    public OrderMenu(Order order, Menu menu) {
        this.order = order;
        this.menu = menu;
    }
}
