package com.woowabros.pilotproject.domain.ordermenu.domain;

import com.woowabros.pilotproject.domain.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findAllByOrder(Order order);
}
