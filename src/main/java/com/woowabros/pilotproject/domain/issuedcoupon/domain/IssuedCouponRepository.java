package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import com.woowabros.pilotproject.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
    Optional<IssuedCoupon> findByCouponCode(CouponCode couponCode);

    List<IssuedCoupon> findAllByMember(Member member);
}
