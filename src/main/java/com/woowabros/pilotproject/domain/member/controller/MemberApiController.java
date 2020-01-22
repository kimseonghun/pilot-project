package com.woowabros.pilotproject.domain.member.controller;

import com.woowabros.pilotproject.config.resolver.SessionMember;
import com.woowabros.pilotproject.domain.common.domain.ErrorResponse;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.dto.MemberCreateDto;
import com.woowabros.pilotproject.domain.member.dto.MemberLoginDto;
import com.woowabros.pilotproject.domain.member.dto.MemberResponseDto;
import com.woowabros.pilotproject.domain.member.exception.NotValidMemberCreateException;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.woowabros.pilotproject.config.resolver.SessionMemberArgumentResolver.SESSION_MEMBER;

@RestController
@RequestMapping("/api/v1/member")
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> save(@Valid @RequestBody MemberCreateDto member, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidMemberCreateException();
        }
        MemberResponseDto newMember = memberService.save(member.toEntity());
        return ResponseEntity.ok(newMember);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginDto member, HttpSession session) {
        Member loginedMember = memberService.login(member.toEntity());
        session.setAttribute(SESSION_MEMBER, SessionMember.builder()
                .memberId(loginedMember.getId())
                .memberName(loginedMember.getMemberName())
                .build());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
