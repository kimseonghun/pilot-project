package com.woowabros.pilotproject.domain.coupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @GetMapping("/admin")
    public String renderCouponAdminPage() {
        return "coupon-admin";
    }

    @GetMapping("/issue")
    public String renderCouponIssuePage() {
        return "coupon-issue";
    }
}
