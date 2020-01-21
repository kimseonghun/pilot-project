package com.woowabros.pilotproject.domain.ordermenu.service;

import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.menu.service.MenuService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMenuService {
    private final OrderMenuRepository orderMenuRepository;
    private final MenuService menuService;

    public OrderMenuService(OrderMenuRepository orderMenuRepository, MenuService menuService) {
        this.orderMenuRepository = orderMenuRepository;
        this.menuService = menuService;
    }

    @Transactional
    public OrderMenu save(Order order, Long menuId) {
        Menu menu = menuService.findById(menuId);
        OrderMenu orderMenu = OrderMenu.builder()
                .order(order)
                .menu(menu)
                .build();

        return orderMenuRepository.save(orderMenu);
    }

    public List<MenuResponseDto> findByOrder(Order order) {
        return orderMenuRepository.findAllByOrder(order).stream()
                .map(OrderMenu::getMenu)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .menuId(menu.getId())
                .menuName(menu.getName())
                .menuPrice(menu.getPrice())
                .build();
    }
}
