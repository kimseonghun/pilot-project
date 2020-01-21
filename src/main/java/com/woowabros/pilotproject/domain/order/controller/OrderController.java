package com.woowabros.pilotproject.domain.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public String renderOrderPage() {
        return "order";
    }

    @GetMapping("/history")
    public String renderOrderHistoryPage() {
        return "order-history";
    }
}
