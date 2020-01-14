package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMember(Member member);
}
