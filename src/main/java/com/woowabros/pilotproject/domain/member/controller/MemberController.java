package com.woowabros.pilotproject.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/signup")
    public String renderSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String renderLogInPage() {
        return "login";
    }
}
