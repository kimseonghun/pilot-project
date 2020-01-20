package com.woowabros.pilotproject.domain.issuedcoupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.service.CouponInnerService;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCouponRepository;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponCode;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotFoundIssuedCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IssuedCouponService {
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponInnerService couponInnerService;
    private final MemberService memberService;

    public IssuedCouponService(IssuedCouponRepository issuedCouponRepository, CouponInnerService couponInnerService, MemberService memberService) {
        this.issuedCouponRepository = issuedCouponRepository;
        this.couponInnerService = couponInnerService;
        this.memberService = memberService;
    }

    @Transactional
    public IssuedCoupon create(Coupon coupon) {
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode(generateCouponCode())
                .coupon(coupon)
                .build();

        return issuedCouponRepository.save(issuedCoupon);
    }

    private String generateCouponCode() {
        String code = UUID.randomUUID().toString();
        Optional<IssuedCoupon> issuedCoupon = issuedCouponRepository.findByCouponCode(CouponCode.of(code));

        if (issuedCoupon.isPresent()) {
            code = generateCouponCode();
        }

        return code;
    }

    @Transactional
    public IssuedCoupon issue(Long couponId, Long memberId) {
        Member member = memberService.findById(memberId);
        Coupon coupon = couponInnerService.findUsableCouponById(couponId);

        IssuedCoupon issuableCoupon = coupon.getIssuedCoupons().stream()
                .filter(IssuedCoupon::isIssuableStatus)
                .filter(issuedCoupon -> hasNotCoupon(issuedCoupon, member))
                .findAny()
                .orElseThrow(NotIssuableCouponException::new);

        return issuableCoupon.issueTo(member);
    }

    private boolean hasNotCoupon(IssuedCoupon issuedCoupon, Member member) {
        List<Coupon> issuedCoupons = issuedCouponRepository.findAllByMember(member).stream()
                .map(IssuedCoupon::getCoupon)
                .collect(Collectors.toList());

        return !issuedCoupons.contains(issuedCoupon.getCoupon());
    }

    public List<IssuedCouponResponseDto> findUsableCouponsByMemberId(Long memberId) {
        Member member = memberService.findById(memberId);

        return issuedCouponRepository.findAllByMember(member).stream()
                .filter(IssuedCoupon::isUsableStatus)
                .filter(issuedCoupon -> issuedCoupon.getCoupon().isUsableDate())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<IssuedCouponResponseDto> findUsedCouponByOrderId(Order order) {
        return issuedCouponRepository.findAllByOrder(order).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public IssuedCoupon useCoupons(String couponCode, Order order) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findByCouponCode(CouponCode.of(couponCode))
                .orElseThrow(NotFoundIssuedCouponException::new);

        return issuedCoupon.use(order);
    }

    private IssuedCouponResponseDto toDto(IssuedCoupon issuedCoupon) {
        return IssuedCouponResponseDto.builder()
                .couponCode(issuedCoupon.getCouponCode().getCode())
                .couponName(issuedCoupon.getCoupon().getName())
                .couponPrice(issuedCoupon.getCoupon().getPrice())
                .build();
    }
}
