package com.woowabros.pilotproject.domain.coupon.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findAll(Pageable pageable);
}
