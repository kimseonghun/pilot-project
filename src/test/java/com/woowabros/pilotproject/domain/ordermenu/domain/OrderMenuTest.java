package com.woowabros.pilotproject.domain.ordermenu.domain;

import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.order.domain.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OrderMenuTest {

    @Test
    void 주문_메뉴_생성_테스트() {
        // given
        OrderMenu orderMenu = OrderMenu.builder()
                .order(mock(Order.class))
                .menu(mock(Menu.class))
                .build();

        // when & then
        assertThat(orderMenu).isEqualTo(OrderMenu.builder()
                .order(mock(Order.class))
                .menu(mock(Menu.class))
                .build());
    }
}