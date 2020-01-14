package com.woowabros.pilotproject.domain.ordermenu.service;

import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.menu.service.MenuService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderMenuServiceTest {

    @InjectMocks
    private OrderMenuService orderMenuService;

    @Mock
    private OrderMenuRepository orderMenuRepository;

    @Mock
    private MenuService menuService;

    private Menu menu;
    private Order order;
    private OrderMenu orderMenu;

    @BeforeEach
    void setUp() {
        menu = mock(Menu.class);
        order = mock(Order.class);
        orderMenu = OrderMenu.builder()
                .order(order)
                .menu(menu)
                .build();
    }

    @Test
    void 주문_메뉴_저장_테스트() {
        // given
        given(menuService.findById(anyLong())).willReturn(menu);
        given(orderMenuRepository.save(any())).willReturn(orderMenu);

        // when
        OrderMenu result = orderMenuService.save(order, 1L);

        // then
        assertThat(result).isEqualTo(orderMenu);
    }

    @Test
    void 주문별_메뉴_목록_조회_테스트() {
        // given
        given(orderMenuRepository.findAllByOrder(any())).willReturn(Collections.singletonList(orderMenu));

        // when
        List<MenuResponseDto> menus = orderMenuService.findByOrder(mock(Order.class));

        // then
        assertThat(menus).contains(MenuResponseDto.builder()
                .menuName(menu.getName())
                .menuPrice(menu.getPrice())
                .build());
    }
}