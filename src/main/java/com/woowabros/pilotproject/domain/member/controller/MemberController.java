package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.dto.MemberCreateDto;
import com.woowabros.pilotproject.domain.member.dto.MemberLoginDto;
import com.woowabros.pilotproject.domain.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.domain.member.exception.NotValidMemberCreateException;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/save")
    public String save(@Valid MemberCreateDto member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            throw new NotValidMemberCreateException();
        }
        MemberResponseDto newMember = memberService.save(member.toEntity());
        model.addAttribute("member", newMember);
        return "index";
    }

    @PostMapping("/login")
    public String login(MemberLoginDto member, HttpSession session) {
        Member loginedMember = memberService.login(member.toEntity());
        session.setAttribute("member", loginedMember);
        return "index";
    }
}
