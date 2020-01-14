package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.exception.CannotCancelOrderException;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class OrderTest {
    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .payment(PaymentType.CARD)
                .member(mock(Member.class))
                .build();
    }

    @Test
    void 주문_생성_테스트() {
        // when & then
        assertThat(order).isEqualTo(Order.builder()
                .payment(PaymentType.CARD)
                .member(mock(Member.class))
                .build());
    }

    @Test
    void 총_주문_금액_계산_테스트() {
        // given
        Menu chicken = Menu.builder().name("치킨").price(20000).build();
        Menu pizza = Menu.builder().name("피자").price(36000).build();
        OrderMenu orderMenu1 = OrderMenu.builder().menu(chicken).order(order).build();
        OrderMenu orderMenu2 = OrderMenu.builder().menu(pizza).order(order).build();
        List<OrderMenu> orderMenus = Arrays.asList(orderMenu1, orderMenu2);

        // when
        Order result = order.calculateTotalPrice(orderMenus);

        // then
        assertThat(result.getTotalPrice()).isEqualTo(chicken.getPrice() + pizza.getPrice());
    }

    @Test
    void 주문_취소_테스트() {
        // when
        Order result = order.cancel();

        // then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    void 주문_취소_시_이미_취소된_경우_예외_테스트() {
        // given
        Order canceledOrder = order.cancel();

        // when & then
        assertThrows(CannotCancelOrderException.class, canceledOrder::cancel);
    }
}